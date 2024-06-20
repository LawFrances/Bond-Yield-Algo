import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class YieldCurveTest {

    private YieldCurve yieldCurve;

    @BeforeEach
    public void setUp() {
        List<String> dates = List.of("2024/05/17", "2024/08/15", "2024/11/13", "2025/02/11", "2025/05/12",
                                     "2025/08/10", "2025/11/08", "2026/02/06", "2026/05/07");
        List<Double> bidRates = List.of(4.50, 5.00, 6.00, 7.20, 7.60, 8.10, 9.00, 10.00, 11.30);
        List<Double> askRates = List.of(4.55, 5.05, 6.05, 7.25, 7.65, 8.15, 9.05, 10.05, 11.35);

        yieldCurve = new YieldCurve(dates, bidRates, askRates);
    }

    @Test
    public void testGetRateExactMatchAsk() {
        assertEquals(4.55, yieldCurve.getRate("17 May 2024", "ask"));
    }

    @Test
    public void testGetRateExactMatchBid() {
        assertEquals(8.1, yieldCurve.getRate("10 Aug 2025", "bid"));
    }

    @Test
    public void testGetRateExactMatchBid1() {
        assertEquals(10, yieldCurve.getRate("06 Feb 2026", "bid"));
    }

    @Test
    public void testGetRateExactMatchAsk1() {
        assertEquals(6.05, yieldCurve.getRate("13 Nov 2024", "ask"));
    }

    @Test
    public void testGetRateDateBeforeRange() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            yieldCurve.getRate("2020/01/17", "bid");
        });
        assertEquals("Date is before the first date i.e '17 May 2024'", exception.getMessage());
    }

    
}
