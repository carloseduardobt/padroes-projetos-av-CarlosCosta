package questao2.observer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class Money {
    private static final DecimalFormat FORMAT = new DecimalFormat(
        "#,##0.00", DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"))
    );
    private Money() { }
    public static synchronized String format(BigDecimal value) {
        return "R$ " + FORMAT.format(value);
    }
}
