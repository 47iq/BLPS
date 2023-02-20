package org.iq47.network;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TicketDTO {

    private Long userId;
    private double coordinateX;
    private double coordinateY;
    private double radius;
    private Boolean hit;
    private LocalDateTime ldt;
    private String time;
    private Long pointId;

    public static Builder newBuilder() {
        return new TicketDTO().new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO that = (TicketDTO) o;
        return Double.compare(that.pointId, pointId) == 0 && Double.compare(that.coordinateX, coordinateX) == 0 && Double.compare(that.coordinateY, coordinateY) == 0 && Double.compare(that.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointId, coordinateX, coordinateY, radius);
    }

    //builder
    public class Builder {
        private Builder() {
        }

        public Builder setCoordinateX(double coordinateX) {
            TicketDTO.this.coordinateX = coordinateX;
            return this;
        }

        public Builder setCoordinateY(double coordinateY) {
            TicketDTO.this.coordinateY = coordinateY;
            return this;
        }

        public Builder setRadius(double radius) {
            TicketDTO.this.radius = radius;
            return this;
        }

        public Builder setHit(Boolean hit) {
            TicketDTO.this.hit = hit;
            return this;
        }

        public Builder setLocalTime(LocalDateTime ldt) {
            TicketDTO.this.ldt = ldt;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            time = ldt.format(formatter);
            return this;
        }

        public Builder setUserId(Long userId) {
            TicketDTO.this.userId = userId;
            return this;
        }

        public Builder setPointId(Long pointId) {
            TicketDTO.this.pointId = pointId;
            return this;
        }

        public TicketDTO build() {
            return TicketDTO.this;
        }
    }
}
