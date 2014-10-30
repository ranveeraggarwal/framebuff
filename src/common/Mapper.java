package common;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 *Generic Mapper for every Class type with compatible java type with sql Types 
 * @author dheerendra Created on Jun 29, 2014
 *
 * @param <T>
 */
public class Mapper<Type> implements ResultSetMapper<Type> {
  
  /**
   * Final Class in which you want you response
   */
  private Class<Type> clazz;
  /**
   * Character by which your sql column names are linked <br>
   * eg. a.b.c.fieldName , here linker is '.' <br>
   * OR a_b_c_fieldName, here linker is '_'
   */
  private Character linker = '_';
  private static final Logger LOG = LoggerFactory.getLogger(Mapper.class);
  
  /**
   * Constructor to be used When default linker '_' is assumed
   * This is Constructor of GenericMapper.java
   * @param clazz
   */
  public Mapper(Class<Type> clazz) {
    this.clazz = clazz;
  }
  /**
   * 
   * This is Constructor of GenericMapper.java
   * @param clazz
   * @param linker
   */
  public Mapper(Class<Type> clazz, Character linker) {
    this.clazz = clazz;
    this.linker = linker;
  }
  
  /**
   * Function of Registering converters from Object to type<br>
   * Must Register a converter when non java.* classes are used
   * @param converter
   * @param type
   * @see Converter
   * @see AbstractConverter
   */
  public void register(Converter converter, Class<?> type) {
    ConvertUtils.register(converter, type);
  }
  
  /**
   * Mapper Function
   * <ul> How it works 
   * <li> First it extracts all columns present in ResultSet <br>
   * Using ResultSetMetaData </li>
   * <li> Call the function {@link #createMap(List)}</li>
   * <li> Call function {@link #mapHelper(String, Map, ResultSet, Class)}</li>
   * </ul>
   * @see ResultSetMapper#map(int, ResultSet, StatementContext)
   * 
   * 
   */
  @Override
  public Type map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
    
    ResultSetMetaData rsmt = rs.getMetaData();
    /**
     * Getting all columns present in ResultSetMetaData
     */
    List<String> columnName = new ArrayList<String>();
    Integer columnCount = rsmt.getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      columnName.add(rsmt.getColumnName(i));
    }
    
    Map<String, List<String>> map = createMap(columnName);
    Type returnObject = null;
    try {
      returnObject = mapHelper("", map, rs, clazz);
    } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
      LOG.info("Error in Generic Mapper", e);
    }
    
    return returnObject;
    
  }
  
  /**
   * How it works
   * <ol>
   * <li> First it creates a newInstance of clazz </li>
   * <li> Get all fields (public+ private) and create a HashMap <br>
   * of form (fieldName(in lower case), Field) which will be used for <br>
   * mapping columns from resultSet to Fields</li>
   * <li> Get simple fields (non nested classes like int, Integer, Strings etc) <br>
   * and set their property by fetching objects from ResultSet with <br>
   * their fieldNames </li>
   * <li> For non primitive Objects, it create another nested map with their property<br>
   * and recurse it on mapHelper with same ResultSet and same new classType</li>
   * <li> Return the constructed Object </li>
   * </ol>
   * @param prepand
   * @param map
   * @param rs
   * @param clazz
   * @return mapped object of type Type from resultset
   * @throws InstantiationException
   * @throws SQLException
   * @throws IllegalAccessException
   * @throws NoSuchFieldException
   * @throws SecurityException
   */
  private Type mapHelper(String prepand, Map<String, List<String>> map, ResultSet rs, Class<Type> clazz)
      throws InstantiationException, SQLException, IllegalAccessException, NoSuchFieldException, SecurityException {
    /**
     * Instantiating Object of Classs
     */
    Type clazzInst = clazz.newInstance();
    
    /**
     * Getting list of all fields in class (private + public)
     */
    Field[] fields = clazz.getDeclaredFields();
    Map<String, Field> fieldMap = new HashMap<String, Field>();
    /**
     * Mapping name of fields to themselves for sql columns (as they are in lowercase by default)
     */
    for (Field fieldIter : fields) {
      fieldMap.put(fieldIter.getName().toLowerCase(), fieldIter);
    }
    
    for (Entry<String, List<String>> entry : map.entrySet()) {
      
      String key = entry.getKey();
      
      if (key == "default") {
        
        List<String> values = entry.getValue();
        for (String str : values) {
          
          /**
           * Getting field which is mapped to column in sql resultset
           */
          Field field = fieldMap.get(str.toLowerCase());
          field.setAccessible(true);
          
          /**
           * Setting value to field of clazzInst from resultset
           */
          try {
            BeanUtils.setProperty(clazzInst, field.getName(), rs.getObject(prepand + str));
          } catch (InvocationTargetException e) {
            LOG.info(
                "Invocation Exception while setting property to " + field.getName() + " in class " + clazz.getName(), e);
          }
        }
      } else {
        
        Map<String, List<String>> tempMap = createMap(entry.getValue());
        
        Field field = fieldMap.get(key.toLowerCase());
        field.setAccessible(true);
        
        try {
        
          BeanUtils.setProperty(clazzInst, field.getName(),
              mapHelper(prepand + key + linker, tempMap, rs, (Class<Type>) field.getType()));
        } catch (InvocationTargetException e) {
          LOG.info(
              "Invocation Exception while setting property to " + field.getName() + " in class " + clazz.getName(), e);
        }
      }
    }
    return clazzInst;
  }
  
  /**
   * Create a Map of nested objects of level 1 <br>
   * eg. for column names {"id","text","classAInstant_id","classBInstant_id","classAInstant_name","a_b_c","a_b_d"}<br>
   * will convert to ["default" => {"id","text"}, "classAInstant" => {"id","name"}, "classBInstant" => {"id"}, "a" => {"b_c","b_d"}]
   * @param list
   * @return
   */
  private Map<String, List<String>> createMap(List<String> list) {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    
    for (String str : list) {
      /**
       * Checking index of linker<br>
       * Checking whether linker is present or not
       */
      int index = str.indexOf(linker);
      /**
       * In case of linker is not there
       */
      if (index == -1) {
        List<String> temp = map.get("default");
        if (temp == null) {
          temp = new ArrayList<String>();
        }
        temp.add(str);
        map.put("default", temp);
      } else {
        /**
         * Getting substring till first instant of linker
         */
        String abc = str.substring(0, index);
        List<String> temp = map.get(abc);
        if (temp == null) {
          temp = new ArrayList<String>();
        }
        /**
         * Getting substring after the first linkers
         */
        temp.add(str.substring(index + 1));
        map.put(abc, temp);
      }
    }
    
    return map;
  }
  
}