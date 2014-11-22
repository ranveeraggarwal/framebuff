from scrapy.spider import Spider
from scrapy.item import Item, Field
from scrapy.selector import Selector
from link_extract import link_list

class FeatureItem(Item):
    title = Field()
    runtime = Field()
    #poster = Field()
    genre = Field()
    #relatedLinks = Field()
    language = Field()
    countryCode = Field()
    synopsis = Field()
    certification = Field()
    director = Field()
    producer = Field()
    actor = Field()

class FeatureSpider(Spider):
    name = "review"
    allowed_domains = ["www.imdb.com"]
    print link_list
    start_urls = [
			 "http://www.imdb.com/title/tt0111161/?ref_=chttp_tt_1",
	]

    def parse(self, response):
        sel = Selector(response)
        item = FeatureItem()
        item['title'] = sel.xpath('//*[contains(concat( " ", @class, " " ), concat( " ", "header", " " ))]//*[contains(concat( " ", @class, " " ), concat( " ", "itemprop", " " ))]/text()').extract()
        item['runtime'] = sel.xpath('//*[(@id = "overview-top")]//time/text()').extract()
        #item['poster'] = sel.xpath('//*[contains(concat( " ", @class, " " ), concat( " ", "itemprop", " " ))]/text()').extract()
        item['genre'] = sel.xpath('//*[contains(concat( " ", @class, " " ), concat( " ", "infobar", " " ))]//*[contains(concat( " ", @class, " " ), concat( " ", "itemprop", " " ))]/text()').extract()
        #item['relatedLinks'] = sel.xpath('//*[contains(concat( " ", @class, " " ), concat( " ", "infobar", " " ))]//*[contains(concat( " ", @class, " " ), concat( " ", "itemprop", " " ))]/text()').extract()
        item['language'] = sel.css('.txt-block a::text').extract()[6]
        item['countryCode'] = sel.css('.txt-block a::text').extract()[5]
        item['synopsis'] = sel.xpath('//*[(@id = "overview-top")]//p/text()').extract()
        item['certification'] = sel.css('hr+ .txt-block h4+ span::text').extract()
        item['director'] = sel.css('p+ .txt-block .itemprop::text').extract()
        item['producer'] = sel.css('#titleDetails .itemprop::text').extract()
        item['actor'] = sel.css('.txt-block~ .txt-block+ .txt-block .itemprop::text').extract()
        return item

