package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value{

    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress()
    {
        return this.address;
    }

    public Type getLocationType()
    {
        return this.locationType;
    }

    @Override
    public Type getType() {
        return new RefType(this.locationType);
    }

    @Override
    public String toString()
    {
        return String.format("RefValue(%d, %s)", address, locationType);
    }
}
