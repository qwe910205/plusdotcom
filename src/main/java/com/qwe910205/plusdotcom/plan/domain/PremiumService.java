package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.ImageSource;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"})
@Entity
public class PremiumService implements Service {

    @AttributeOverride(name = "value", column = @Column(name = "NAME"))
    @EmbeddedId
    private ServiceName name;

    @AttributeOverride(name = "url", column = @Column(name = "image"))
    @Embedded
    private ImageSource image;

    public PremiumService(String name, String image) {
        this.name = new ServiceName(name);
        this.image = new ImageSource(image);
    }

    @Override
    public String getName() {
        return name.getValue();
    }

    @Override
    public String getImage() {
        return image.getUrl();
    }
}
