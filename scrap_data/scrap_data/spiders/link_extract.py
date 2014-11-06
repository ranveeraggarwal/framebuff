from scrapy.spider import Spider
from scrapy.item import Item, Field
from scrapy.selector import Selector
'''
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
    '''

class LinkItem(Item):
	links = Field()
class FeatureSpider(Spider):
    name = "link"
    allowed_domains = ["www.imdb.com"]
    start_urls = [
			 "http://www.imdb.com/chart/top?ref_=nv_ch_250_4",
	]

    def parse(self, response):
        sel = Selector(response)
        item = LinkItem()
        item['links'] = sel.css('#main a').extract()
        
        return item

