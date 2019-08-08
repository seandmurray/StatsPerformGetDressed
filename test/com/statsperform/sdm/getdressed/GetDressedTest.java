package com.statsperform.sdm.getdressed;

import static org.junit.Assert.*;

import org.junit.Test;

public class GetDressedTest {

	@Test
	public void test() {
		assertEquals("Some clothes are required!", "fail", GetDressed.go());
		assertEquals("Invalid Ids?", "fail", GetDressed.go(7, 8));

		assertEquals("All the requirements but has a duplicate", "fail", GetDressed.go(2, 5, 5, 4, 3, 6));
		assertEquals("All the requirements but has a duplicate", "fail", GetDressed.go(2, 5, 4, 3, 6, 6));

		assertEquals("Shoes before socks fails", "fail", GetDressed.go(4, 5));
		assertEquals("Shoes before pants fails", "fail", GetDressed.go(4, 2));
		assertEquals("Hat before shirt fails", "fail", GetDressed.go(1, 3));
		assertEquals("Socks depend on shoes", "socks, fail", GetDressed.go(5, 1));
		assertEquals("Got all the dependences but the expeced last id is missing", "pants, socks, shoes, shirt, fail",
				GetDressed.go(2, 5, 4, 3));

		assertEquals("pants, socks, shoes, shirt, leave", GetDressed.go(2, 5, 4, 3, 6));
		assertEquals("pants, socks, shirt, shoes, leave", GetDressed.go(2, 5, 3, 4, 6));
		assertEquals("socks, pants, shirt, shoes, leave", GetDressed.go(5, 2, 3, 4, 6));
		assertEquals("socks, pants, shirt, shoes, hat, leave", GetDressed.go(5, 2, 3, 4, 1, 6));
		assertEquals("pants, socks, shirt, hat, shoes, leave", GetDressed.go(2, 5, 3, 1, 4, 6));
	}

}
