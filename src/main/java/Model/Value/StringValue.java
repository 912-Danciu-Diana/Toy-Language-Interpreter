package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{

    private String value;

    public StringValue(String value){
        this.value = value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object anotherValue) {
        return anotherValue instanceof StringValue;
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }
}
