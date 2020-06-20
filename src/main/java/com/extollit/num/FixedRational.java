package com.extollit.num;

public final class FixedRational {
    public static final PrecisionType DEFAULT_PRECISION = new PrecisionType(4);

    public final PrecisionType precision;

    private double delegate;

    public FixedRational(PrecisionType precisionType, double delegate)
    {
        this.precision = precisionType;
        this.delegate = delegate;
    }
    public FixedRational(FixedRational other) {
        this.delegate = other.delegate;
        this.precision = other.precision;
    }

    public double doubleValue() {
        return this.precision.round(this.delegate);
    }

    public double raw() { return this.delegate; }

    public void roundOff(int places) {
        this.delegate = this.precision.roundOff(places, this.delegate);
    }

    public void add(FixedRational other) {
        this.delegate += other.delegate;
    }
    public void sub(FixedRational other) {
        this.delegate -= other.delegate;
    }
    public void times(FixedRational other) {
        this.delegate *= other.delegate;
    }
    public void divide(FixedRational other) {
        this.delegate /= other.delegate;
    }
    public void set(FixedRational other) {
        this.delegate = other.delegate;
    }

    public void add(double value) {
        this.delegate += value;
    }
    public void sub(double value) {
        this.delegate -= value;
    }
    public void times(double value) {
        this.delegate *= value;
    }
    public void divide(double value) {
        this.delegate /= value;
    }
    public void set(double value) {
        this.delegate = value;
    }

    public void negate() {
        this.delegate = -this.delegate;
    }

    public boolean nonZero() { return this.precision.nonZero(this.delegate); }

    public void max(FixedRational other) {
        if (other.delegate > this.delegate)
            this.delegate = other.delegate;
    }

    public void min(FixedRational other) {
        if (other.delegate < this.delegate)
            this.delegate = other.delegate;
    }

    public boolean equals(double other) {
        return this.precision.equal(other, this.delegate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedRational that = (FixedRational) o;

        return this.precision.equal(that.delegate, delegate);
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits((float)this.precision.round(this.delegate));
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return this.precision.toString(doubleValue());
    }

    public boolean positive() {
        return this.delegate > 0;
    }

    public void project(FixedRational other) {
        if (equals(other)) {
            if (other.delegate < this.delegate)
                this.delegate = this.precision.ceil(this.delegate);
            else if (other.delegate > this.delegate)
                this.delegate = this.precision.floor(this.delegate);
        }
    }

    public boolean lessThan(FixedRational other) {
        return this.delegate < other.delegate;
    }

    public boolean greaterThan(FixedRational other) {
        return this.delegate > other.delegate;
    }
}
