package engenharia.economica.app.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class MathCommons {
    
    private MathCommons() {
    }
    
    public static final BigDecimal  DIAS_NO_MES  = new BigDecimal(30);
    public static final BigDecimal  MESES_NO_ANO = new BigDecimal(12);
    public static final BigDecimal  DIAS_NO_ANO  = new BigDecimal(365);
    public static final MathContext MATH_CONTEXT = new MathContext(2, RoundingMode.HALF_EVEN);
}