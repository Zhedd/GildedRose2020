package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	
	@Test
	public void qualityAndSellInTest() {
		//create an inn, call the main-method
		GildedRose inn = new GildedRose();
		GildedRose.main(null);
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		//assert quality and sellIn has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
		assertEquals("Failed sell by date value for Dexterity Vest", 9, sellIn);
	}
	
	@Test
	public void qualityAndSellInDegradationTest() {
		//create an inn, add an item, and simulate ten days
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Milk", 10, 20));
		int i;
		for (i = 0; i < 10; i++) {
			inn.oneDay();
		}
		
		//access a list of items, get the quality and sell by date of Milk
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		//assert quality and sell by date has decreased by ten
		assertEquals("Failed quality for Milk", 10, quality);
		assertEquals("Failed sell by date value for Milk", 0, sellIn);

		//one day past sell by date
		inn.oneDay();

		//update variables
		quality = items.get(0).getQuality();
		sellIn = items.get(0).getSellIn();
				
		//assert quality has decreased by 2 and sellIn has decreased by one
		assertEquals("Failed quality for Milk", 8, quality);
		assertEquals("Failed sell by date value for Milk", -1, sellIn);

		//four more days
		for (i = 0; i < 4; i++) {
			inn.oneDay();
		}
		
		//update variables
		quality = items.get(0).getQuality();
		sellIn = items.get(0).getSellIn();
		
		//assert quality has decreased to 0, and sell by date has decreased to -5
		assertEquals("Failed quality for Milk", 0, quality);
		assertEquals("Failed sell by date value for Milk", -5, sellIn);
		
		//one more day
		inn.oneDay();
		
		//update variables
		quality = items.get(0).getQuality();
		sellIn = items.get(0).getSellIn();
		
		//assert quality is still 0, while sell by date has decreased by one
		assertEquals("Failed quality for Milk", 0, quality);
		assertEquals("Failed sell by date for Milk", -6, sellIn);
	}
	
	@Test
	public void agedBrieTest() {
		//create an inn, add the "Aged Brie", simulate two days until sell by date is 0
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 2, 0));
		int i;
		for (i = 0; i < 2; i++) {
			inn.oneDay();
		}
		
		//access a list of items, get the quality of the brie
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert that quality has increased by two
		assertEquals("Failed quality for Aged Brie", 2, quality);
		
		//wait for 10 days
		for (i = 0; i < 10; i++) {
			inn.oneDay();
		}
		
		//assert that quality keeps increasing twice as fast, and is now 22
		quality = items.get(0).getQuality();
		assertEquals("Failed quality for Aged Brie", 22, quality);
		
		//wait for 14 days
		for (i = 0; i < 14; i++) {
			inn.oneDay();
		}
		//assert that quality is now 50
		quality = items.get(0).getQuality();
		assertEquals("Failed quality for Aged Brie", 50, quality);
		
		//wait for 100 days
		for (i = 0; i < 100; i++) {
			inn.oneDay();
		}
				
		//assert that quality is capped at 50
		quality = items.get(0).getQuality();
		assertEquals("Failed quality for Aged Brie", 50, quality);
	}
	
	@Test
	public void sulfurasTest() {
		//create an inn, add the "Sulfuras, Hand of Ragnaros", simulate a day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.oneDay();
		
		//access a list of items, get the quality and sell by date of Sulfuras
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		//assert that neither quality or sell by date has decreased
		assertEquals("Failed quality for Sulfuras", 80, quality);
		assertEquals("Failed sell by date value for Sulfuras", 0, sellIn);
	}
	
	@Test
	public void backstagePassTest() {
		//create an inn, add backstage passes, simulate a day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the passes
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert that quality has increased by one
		assertEquals("Failed quality for Backstage passes", 21, quality);
		
		//wait until 10 days to sell by date
		int i;
		for (i = 0; i < 4; i++) {
			inn.oneDay();
		}
		
		quality = items.get(0).getQuality();
		//assert that quality has increased by one for four days
		assertEquals("Failed quality for Backstage passes", 25, quality);
		
		//wait until 5 days to sell by date
		for (i = 0; i < 5; i++) {
			inn.oneDay();
		}
		
		quality = items.get(0).getQuality();
		//assert that quality has increased by two for five days
		assertEquals("Failed quality for Backstage passes", 35, quality);
		
		//wait until 0 days to sell by date
		for (i = 0; i < 5; i++) {
			inn.oneDay();
		}
		
		quality = items.get(0).getQuality();
		//assert that quality has increased by three*five = 15
		assertEquals("Failed quality for Backstage passes", 50, quality);
		
		//after the concert quality is zero
		inn.oneDay();
		quality = items.get(0).getQuality();
		assertEquals("Failed quality for Backstage passes", 0, quality);
		inn.oneDay();
		quality = items.get(0).getQuality();
		assertEquals("Failed quality for Backstage passes", 0, quality);
	}
	
	@Test
	public void backstagePassTest2() {
		//create an inn, add backstage passes that are able to obtain more than 50 quality if there was not a limit for that
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 20, 20));
		
		//wait until 0 days to sell by date
		int i;
		for (i = 0; i < 20; i++) {
			inn.oneDay();
		}
		
		//access a list of items, get the quality of the passes
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//quality should be capped at 50
		assertEquals("Failed quality for Backstage passes", 50, quality);
	}
	
	@Test
	public void startQualityTest() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Milk", 1, 60));
		inn.oneDay();
		int quality = inn.getItems().get(0).getQuality();
		assertEquals("Failed quality for Milk", 49, quality);
		
		inn.setItem(new Item("Milk2", 1, 51));
		inn.oneDay();
		quality = inn.getItems().get(1).getQuality();
		assertEquals("Failed quality for Milk2", 49, quality);
		
		inn.setItem(new Item("Milk3", 0, -11));
		inn.oneDay();
		quality = inn.getItems().get(2).getQuality();
		assertEquals("Failed quality for Milk3", 0, quality);
	}
	
	@Test
	public void qualityDegradationTest() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Milk", -1, 1));
		inn.oneDay();
		int quality = inn.getItems().get(0).getQuality();
		assertEquals("Failed quality for Milk", 0, quality);
	}
	
	@Test
	public void agedBrieTest2() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 1, 60));
		inn.oneDay();
		int quality = inn.getItems().get(0).getQuality();
		assertEquals("Failed quality for Aged Brie", 50, quality);
		inn.setItem(new Item("Aged Brie", 1, -60));
		inn.oneDay();
		quality = inn.getItems().get(1).getQuality();
		assertEquals("Failed quality for Aged Brie", 1, quality);
	}
	
	@Test
	public void sulfurasTest2() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", -1, 81));
		inn.oneDay();
		int quality = inn.getItems().get(0).getQuality();
		int sellIn = inn.getItems().get(0).getSellIn();
		assertEquals("Failed sell by date for Sulfuras", 0, sellIn);
		assertEquals("Failed quality for Sulfuras", 80, quality);
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 12, 70));
		inn.oneDay();
		quality = inn.getItems().get(1).getQuality();
		sellIn = inn.getItems().get(1).getSellIn();
		assertEquals("Failed sell by date for Sulfuras", 0, sellIn);
		assertEquals("Failed quality for Sulfuras", 80, quality);
	}
	
	@Test
	public void backstagePassTest3() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to ANOTHER concert", 1, 5));
		inn.oneDay();
		int quality = inn.getItems().get(0).getQuality();
		assertEquals("Failed quality for Backstage passes", 8, quality);
		inn.setItem(new Item("Fake backstage passes to ANOTHER concert", 5, 5));
		inn.oneDay();
		quality = inn.getItems().get(1).getQuality();
		assertEquals("Failed quality for Backstage passes", 4, quality);
		for (int i = 0; i < 3; i++) {
			inn.oneDay();
		}
		quality = inn.getItems().get(1).getQuality();
		assertEquals("Failed quality for Backstage passes", 1, quality);
		for (int i = 0; i < 3; i++) {
			inn.oneDay();
		}
		quality = inn.getItems().get(1).getQuality();
		assertEquals("Failed quality for Backstage passes", 0, quality);
		
		inn.setItem(new Item("Backstage passes to FLASH concert", 1, 49));
		inn.oneDay();
		quality = inn.getItems().get(2).getQuality();
		assertEquals("Failed quality for Backstage passes", 50, quality);
	}
	
	@Test
	public void loopSkipTest() {
		GildedRose inn = new GildedRose();
		inn.oneDay();
	}
}
