import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    @Test
    public void testCompareTo() {
        Ticket ticket1 = new Ticket("MOW", "LED", 5000, 10, 12);
        Ticket ticket2 = new Ticket("MOW", "LED", 7000, 10, 12);
        Ticket ticket3 = new Ticket("MOW", "LED", 5000, 10, 12);

        assertTrue(ticket1.compareTo(ticket2) < 0); // 5000 < 7000
        assertTrue(ticket2.compareTo(ticket1) > 0); // 7000 > 5000
        assertEquals(0, ticket1.compareTo(ticket3)); // одинаковая цена
    }

    @Test
    public void testSearch() {
        AviaSouls aviaSouls = new AviaSouls();
        Ticket ticket1 = new Ticket("MOW", "LED", 5000, 10, 12);
        Ticket ticket2 = new Ticket("MOW", "LED", 3000, 9, 11);
        Ticket ticket3 = new Ticket("MOW", "LED", 7000, 14, 16);
        Ticket ticket4 = new Ticket("LED", "MOW", 4000, 10, 12);

        aviaSouls.add(ticket1);
        aviaSouls.add(ticket2);
        aviaSouls.add(ticket3);
        aviaSouls.add(ticket4);

        Ticket[] expected = {ticket2, ticket1, ticket3};
        Ticket[] actual = aviaSouls.search("MOW", "LED");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testTicketTimeComparator() {
        Ticket ticket1 = new Ticket("MOW", "LED", 5000, 10, 12); // Время в пути: 2 ч
        Ticket ticket2 = new Ticket("MOW", "LED", 3000, 9, 11);  // Время в пути: 2 ч
        Ticket ticket3 = new Ticket("MOW", "LED", 7000, 14, 17); // Время в пути: 3 ч

        TicketTimeComparator comparator = new TicketTimeComparator();

        assertEquals(0, comparator.compare(ticket1, ticket2));  // Одинаковая длительность
        assertTrue(comparator.compare(ticket1, ticket3) < 0);   // 2 ч < 3 ч
        assertTrue(comparator.compare(ticket3, ticket1) > 0);   // 3 ч > 2 ч
    }

    @Test
    void testSearchAndSortBy() {
        Ticket[] tickets = {
                new Ticket("MOW", "LED", 3000, 9, 11),
                new Ticket("MOW", "LED", 5000, 10, 12),
                new Ticket("MOW", "LED", 2000, 8, 10),
                new Ticket("MOW", "LED", 4000, 7, 9)
        };

        Arrays.sort(tickets, Comparator.comparingInt(Ticket::getPrice)
                .thenComparingInt(Ticket::getTimeFrom));

        Ticket[] expectedTickets = {
                new Ticket("MOW", "LED", 2000, 8, 10),
                new Ticket("MOW", "LED", 3000, 9, 11),
                new Ticket("MOW", "LED", 4000, 7, 9),
                new Ticket("MOW", "LED", 5000, 10, 12)
        };

        assertArrayEquals(expectedTickets, tickets);
    }


    @Test
    public void testSearchMultipleTicketsFound() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket1 = new Ticket("MOW", "LED", 5000, 10, 12);
        Ticket ticket2 = new Ticket("MOW", "LED", 3000, 9, 11);
        Ticket ticket3 = new Ticket("MOW", "LED", 4000, 8, 10);
        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);

        Ticket[] expected = {ticket2, ticket3, ticket1};
        Ticket[] actual = souls.search("MOW", "LED");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSearchSingleTicketFound() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket1 = new Ticket("MOW", "LED", 5000, 10, 12);
        Ticket ticket2 = new Ticket("NYC", "LAX", 6000, 14, 18);
        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] expected = {ticket1};
        Ticket[] actual = souls.search("MOW", "LED");

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSearchNoTicketsFound() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket1 = new Ticket("CityA", "CityB", 5000, 10, 12);
        Ticket ticket2 = new Ticket("CityC", "CityD", 6000, 14, 18);
        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] expected = {};
        Ticket[] actual = souls.search("CityX", "CityY");

        assertArrayEquals(expected, actual);
    }

}


