package viewstate.challenges.pdm.parcelablecounter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class whose instances represent counters.
 */
public class Counter implements Parcelable {

    /** The counter value */
    private int value;
    /** The counter's maximum value */
    private final int maxValue;

    /**
     * Factory that creates Counter instances from the given {@link Parcel}
     * Provided in order to adhere to the {@link }Parcelable} convention.
     */
    public final static Creator<Counter> CREATOR = new Creator<Counter>() {

        /** {@inheritDoc} */
        @Override
        public Counter createFromParcel(Parcel source) {
            return new Counter(source);
        }

        /** {@inheritDoc} */
        @Override
        public Counter[] newArray(int size) {
            return new Counter[size];
        }
    };

    /**
     * Initiates with instance from the given data.
     * @param data
     */
    private Counter(Parcel data) {
        this(data.readInt(), data.readInt());
    }

    public Counter(int initialValue, int maxValue) {
        this.value = initialValue;
        this.maxValue = maxValue;
    }

    public int increment() {
        return value = ++value % maxValue;
    }

    public int decrement() {
        return value = value == 0 ? maxValue-1 : value-1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
        dest.writeInt(maxValue);
    }

    public int getValue() {
        return value;
    }
}
