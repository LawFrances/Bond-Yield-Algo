import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

public class YieldCurve {
    private List<LocalDate> dates;
    private List<Double> bidRates;
    private List<Double> askRates;

    public YieldCurve(List<String> dates, List<Double> bidRates, List<Double> askRates) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.dates = dates.stream()
                          .map(date -> LocalDate.parse(date, formatter))
                          .toList();
        this.bidRates = bidRates;
        this.askRates = askRates;
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH))
            .appendOptional(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH))
            .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendOptional(DateTimeFormatter.BASIC_ISO_DATE)
            .toFormatter();

        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    public double getRate(String dateStr, String rateType) {
        LocalDate date = parseDate(dateStr);

        if (date.isBefore(dates.get(0))) {
            throw new IllegalArgumentException("Date is before the first date i.e '17 May 2024'");
        }

        if (date.isAfter(dates.get(dates.size() - 1))) {
            double lastBidRate = bidRates.get(bidRates.size() - 1);
            double lastAskRate = askRates.get(askRates.size() - 1);

            return switch (rateType) {
                case "bid" -> lastBidRate;
                case "ask" -> lastAskRate;
                case "mid" -> (lastBidRate + lastAskRate) / 2;
                default -> throw new IllegalArgumentException("Invalid rate type");
            };
        }

        for (int i = 1; i < dates.size(); i++) {
            if (!date.isAfter(dates.get(i))) {
                LocalDate prevDate = dates.get(i - 1);
                LocalDate nextDate = dates.get(i);
                long totalDays = ChronoUnit.DAYS.between(prevDate, nextDate);
                long daysFromPrev = ChronoUnit.DAYS.between(prevDate, date);
                double t = (double) daysFromPrev / totalDays;

                double prevRate, nextRate;

                switch (rateType) {
                    case "bid":
                        prevRate = bidRates.get(i - 1);
                        nextRate = bidRates.get(i);
                        break;
                    case "ask":
                        prevRate = askRates.get(i - 1);
                        nextRate = askRates.get(i);
                        break;
                    case "mid":
                        prevRate = (bidRates.get(i - 1) + askRates.get(i - 1)) / 2;
                        nextRate = (bidRates.get(i) + askRates.get(i)) / 2;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid rate type");
                }

                return prevRate + t * (nextRate - prevRate);
            }
        }

        throw new IllegalArgumentException("Date is out of range");
    }

    public void tryGetRate(String dateStr, String rateType) {
        try {
            double rate = getRate(dateStr, rateType);
            System.out.println(rate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
