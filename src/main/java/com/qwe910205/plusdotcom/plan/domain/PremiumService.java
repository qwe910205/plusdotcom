package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.ImageSource;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"})
@Entity
public class PremiumService {

    @EmbeddedId
    private ServiceName name;

    private ImageSource image;

    public PremiumService(String name, String image) {
        this.name = new ServiceName(name);
        this.image = new ImageSource(image);
    }

    public String getName() {
        return this.name.getName();
    }
}
