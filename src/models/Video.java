package models;

public class Video {
	
	private String title;
	private Integer runtime;
	private Integer type;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the runtime
	 */
	public Integer getRuntime() {
		return runtime;
	}
	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

}
