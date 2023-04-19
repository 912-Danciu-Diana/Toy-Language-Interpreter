package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value {

    private final boolean value;

    public BoolValue(boolean _value)
    {
        this.value = _value;
    }


    @Override
    public Type getType() {
        return new BoolType();
    }
/*
    @Override
    public Value getValue() {
        return new BoolValue(value);
    }
    */

    public boolean getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object anotherValue) {
        return anotherValue instanceof BoolValue;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
}
