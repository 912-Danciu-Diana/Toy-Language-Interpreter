package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value {

    private final int value;

    public IntValue(int _value)
    {
        this.value = _value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object anotherValue) {
        return anotherValue instanceof IntValue;
    }

    @Override
    public String toString() {
        return String.format("%d", this.value);
    }
}
