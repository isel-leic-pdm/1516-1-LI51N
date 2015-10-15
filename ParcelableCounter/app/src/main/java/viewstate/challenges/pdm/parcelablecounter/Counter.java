package viewstate.challenges.pdm.parcelablecounter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class whose instances represent counters. Because class instances are stored as view state (a
 * disputable decision), they may cross process boundaries, and therefore the type must adhere to
 * the {@link Parcelable} contract.
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
     * Private constructor used to initiate an instance from the given {@link Parcel}.
     * @param data The parcel containing the new instance's data.
     */
    private Counter(Parcel data) {
        this(data.readInt(), data.readInt());
    }

    /**
     * Initiates an instance with the given initial value and maximum value.
     * @param initialValue The instance's initial value (in the interval [0..maxValue[)
     * @param maxValue The instance's maximum value.
     */
    public Counter(int initialValue, int maxValue) {
        this.value = initialValue;
        this.maxValue = maxValue;
    }

    /**
     * Increments the counter value. If the upper bound is reached (i.e. maxValue), the counter's
     * value becomes 0.
     * @return The new counter value.
     */
    public int increment() {
        return value = ++value % maxValue;
    }

    /**
     * Decrements the counter value. If the lowe bound had already been reached (i.e. 0), the
     * counter's value becomes maxvalue - 1.
     * @return The new counter value.
     */
    public int decrement() {
        return value = value == 0 ? maxValue-1 : value-1;
    }

    /** {@inheritDoc} */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Stores the instance's state in the given {@link Parcel} instance.
     * (Used to create the object's external representation)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
        dest.writeInt(maxValue);
    }

    /**
     * Gets the current counter value, in the interval [0..maxValue]
     * @return The counter value.
     */
    public int getValue() {
        return value;
    }
}
