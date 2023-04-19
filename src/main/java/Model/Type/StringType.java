package Model.Type;


import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type {
    @Override
    public boolean equals(Type anotherType) {
        return anotherType instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "string";
    }
}
