package com.extollit.linalg;

import com.extollit.linalg.mutable.Vec3d;
import com.extollit.linalg.mutable.Vec3i;

import java.util.Iterator;

public class DiscreteRayStepper implements Iterator<DiscreteRayStepper.Result> {
    public final class Result {
        public final com.extollit.linalg.immutable.Vec3i cell;
        private double ldist;

        protected Result(com.extollit.linalg.immutable.Vec3i cell, double ldist) {
            this.cell = cell;
            this.ldist = ldist;
        }

        public com.extollit.linalg.immutable.Vec3d offset() {
            final com.extollit.linalg.immutable.Vec3d d = DiscreteRayStepper.this.direction;
            final Vec3d p = new Vec3d(d);
            p.mul(this.ldist);

            final double
                atx = partialRatio(p.x, d.x),
                aty = partialRatio(p.y, d.y),
                atz = partialRatio(p.z, d.z),

                t;

            if (atx < aty)
                if (atz < atx)
                    t = atz;
                else
                    t = atx;
            else if (aty < atz)
                t = aty;
            else
                t = atz;

            p.sub(d.x * t, d.y * t, d.z * t);
            return new com.extollit.linalg.immutable.Vec3d(p);
        }

        protected double partialRatio(final double c, final double t) {
            return Math.abs(t == 0 ? 2 : (c - (int)c) / t);
        }
    }

    public final com.extollit.linalg.immutable.Vec3d origin, direction;

    private final com.extollit.linalg.immutable.Vec3i target, delta;
    private final com.extollit.linalg.immutable.Vec3d incrementor;
    private final Vec3i cell;
    private final Vec3d walker, dist = new Vec3d(0, 0, 0);

    private byte mx, my, mz;
    private double ldist, off;

    public DiscreteRayStepper(com.extollit.linalg.immutable.Vec3d origin,
                              com.extollit.linalg.immutable.Vec3d target) {
        this(origin, target.subOf(origin).normalized(), target);
    }

    public DiscreteRayStepper(com.extollit.linalg.immutable.Vec3d origin,
                              com.extollit.linalg.immutable.Vec3d direction,
                              com.extollit.linalg.immutable.Vec3d target) {
        this.origin = origin;
        this.cell =
            new Vec3i(
                (int)Math.floor(origin.x),
                (int)Math.floor(origin.y),
                (int)Math.floor(origin.z)
            );
        this.target =
            new com.extollit.linalg.immutable.Vec3i(
                (int)Math.floor(target.x),
                (int)Math.floor(target.y),
                (int)Math.floor(target.z)
            );

        this.direction = direction;
        this.incrementor = new com.extollit.linalg.immutable.Vec3d(
            Math.abs(direction.x),
            Math.abs(direction.y),
            Math.abs(direction.z)
        );
        this.delta =
            new com.extollit.linalg.immutable.Vec3i(
                direction.x < 0 ? -1 : 1,
                direction.y < 0 ? -1 : 1,
                direction.z < 0 ? -1 : 1
            );

        this.mx = (byte)(((this.delta.x + 1) >> 1) - 1);
        this.my = (byte)(((this.delta.y + 1) >> 1) - 1);
        this.mz = (byte)(((this.delta.z + 1) >> 1) - 1);

        this.walker = new Vec3d(origin);
        this.walker.x -= Math.floor(origin.x);
        this.walker.y -= Math.floor(origin.y);
        this.walker.z -= Math.floor(origin.z);

        if (this.delta.x < 0)
            this.walker.x = -this.walker.x + 1;
        if (this.delta.y < 0)
            this.walker.y = -this.walker.y + 1;
        if (this.delta.z < 0)
            this.walker.z = -this.walker.z + 1;
    }

    @Override
    public Result next() {
        final Vec3d
            dist = this.dist,
            walker = this.walker;

        final com.extollit.linalg.immutable.Vec3d
            incrementor = this.incrementor;

        final Vec3i
            cell = this.cell;
        final com.extollit.linalg.immutable.Vec3i
            delta = this.delta;

        dist.x = 1;
        dist.y = 1;
        dist.z = 1;
        dist.sub(walker);
        dist.x /= incrementor.x;
        dist.y /= incrementor.y;
        dist.z /= incrementor.z;

        while (walker.x < 1 && walker.y < 1 && walker.z < 1)
        {
            ++this.ldist;
            walker.add(incrementor);
        }

        if (walker.x >= 1 && (dist.x <= dist.y && dist.x <= dist.z)) {
            cell.x += delta.x;
            walker.x -= 1;
            this.off = walker.x / incrementor.x;
        } else if (walker.y >= 1 && (dist.y <= dist.x && dist.y <= dist.z)) {
            cell.y += delta.y;
            walker.y -= 1;
            this.off = walker.y / incrementor.y;
        } else {
            cell.z += delta.z;
            walker.z -= 1;
            this.off = walker.z / incrementor.z;
        }

        return new Result(
            new com.extollit.linalg.immutable.Vec3i(cell),
            this.ldist
        );
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        final com.extollit.linalg.immutable.Vec3d direction = this.direction;
        final Vec3i cell = this.cell;
        final com.extollit.linalg.immutable.Vec3i
            target = this.target,
            delta = this.delta;

        return !(direction.x == 0 && direction.y == 0 && direction.z == 0) &&
               (cell.x - target.x) * delta.x <= 0 &&
               (cell.y - target.y) * delta.y <= 0 &&
               (cell.z - target.z) * delta.z <= 0;
    }
}
