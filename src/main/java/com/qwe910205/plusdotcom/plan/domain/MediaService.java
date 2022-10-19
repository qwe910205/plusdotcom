package com.qwe910205.plusdotcom.plan.domain;

import com.qwe910205.plusdotcom.phone.domain.wrapper.ImageSource;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name"})
@Entity
public class MediaService implements Service {

    @EmbeddedId
    private ServiceName name;

    private ImageSource image;

    public MediaService(String name, String image) {
        this.name = new ServiceName(name);
        this.image = new ImageSource(image);
    }

    @Override
    public String getName() {
        return name.getName();
    }

    @Override
    public String getImage() {
        return image.getUrl();
    }
}
