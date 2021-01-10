package demo.rt.out.remove.compound;

import demo.rt.out.remove.compound.base.*;
import lombok.Data;

@Data
public class Compound {
    private Bool bool;
    private Boosting boosting;
    private ConstantScore constantScore;
    private DisMax disMax;
    private FunctionScore functionScore;
}
