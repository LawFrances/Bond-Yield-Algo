import java.util.List;

public class YieldCurveApp {
    public static void main(String[] args) {
        List<String> dates = List.of("2024/05/17", "2024/08/15", "2024/11/13", "2025/02/11", "2025/05/12",
                                     "2025/08/10", "2025/11/08", "2026/02/06", "2026/05/07");
        List<Double> bidRates = List.of(4.50, 5.00, 6.00, 7.20, 7.60, 8.10, 9.00, 10.00, 11.30);
        List<Double> askRates = List.of(4.55, 5.05, 6.05, 7.25, 7.65, 8.15, 9.05, 10.05, 11.35);

        YieldCurve yc = new YieldCurve(dates, bidRates, askRates);
        
        tryGetRate(yc, "2024/07/01", "bid");  // Interpolated bid rate
        tryGetRate(yc, "2024-07-01", "ask");  // Interpolated ask rate
        tryGetRate(yc, "01-Jul-2024", "mid");  // Interpolated mid rate
        tryGetRate(yc, "17 Aug 2024", "bid");  // Interpolated mid rate
        tryGetRate(yc, "2026/06/01", "bid");  // Flat Extrapolated bid rate
        tryGetRate(yc, "17 May 2024", "ask");  // Exact match on the first date
        tryGetRate(yc, "10 Aug 2025", "bid");  // Exact match on the first date
        tryGetRate(yc, "2020/01/17", "bid");  // Should raise an exception and handle it
        tryGetRate(yc, "2019/01/17", "bid");  // Should raise an exception and handle it
        
    }

    private static void tryGetRate(YieldCurve yc, String dateStr, String rateType) {
        try {
            double rate = yc.getRate(dateStr, rateType);
            System.out.println("The " + rateType + " rate for " + dateStr + " is " + rate + "%");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
