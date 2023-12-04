package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.plan.domain.MediaService;
import com.qwe910205.plusdotcom.plan.domain.PremiumService;
import com.qwe910205.plusdotcom.plan.repository.MediaServiceRepository;
import com.qwe910205.plusdotcom.plan.repository.PremiumServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceInitializer implements DataInitializer {

    private final int priority = 0;
    private final PremiumServiceRepository premiumServiceRepository;
    private final MediaServiceRepository mediaServiceRepository;

    @Override
    public void init() {
        initPremiumService();
        initMediaService();
    }

    private void initPremiumService() {
        String[] names = { "넷플릭스+유튜브 프리미엄", "티빙", "디즈니+", "넷플릭스", "유튜브 프리미엄", "헬로렌탈 구독", "일리커피 구독", "구글패키지", "초등나라",
                "갤럭시 버즈", "갤럭시 버즈2 프로", "갤럭시 워치5" };

        String[] images = {
                "https://image.lguplus.com/static/pc-contents/images/fcmm/cnts/imge/20220713-022359-087-oFzvtGNB.png",
                "https://image.lguplus.com/static/pc-contents/images/fcmm/cnts/imge/20220713-022359-087-oFzvtGNB.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-disneyplus.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-netflix.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-youtube-premium.png",
                "https://image.lguplus.com/static/pc-contents/images/fcmm/cnts/imge/20220608-102646-081-52wcuszy.png",
                "https://image.lguplus.com/static/pc-contents/images/fcmm/cnts/imge/20220525-091736-366-52x1YBa0.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-googlepack.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-children.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/plan-sp-buds.png",
                "https://image.lguplus.com/static/pc-contents/images/fcmm/cnts/imge/20220811-025322-772-9rJZdGNg.png",
                "https://image.lguplus.com/static/pc-contents/images/fcmm/cnts/imge/20220811-025420-708-Z7eQKyxC.png" };

        for (int i = 0; i < names.length; i++) {
            PremiumService premiumService = new PremiumService(names[i], images[i]);
            premiumServiceRepository.save(premiumService);
        }
    }

    private void initMediaService() {
        String[] names = { "지니뮤직 앱", "U+영화월정액(구 영화월정액)", "U+아이들 생생도서관", "밀리의 서재" };

        String[] images = {
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/icon/ico-pack-genie.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/icon/ico-pack-movie.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/icon/ico-pack-library.png",
                "https://image.lguplus.com/static/pc-contents/cv2022/images/indv/icon/ico-pack-millie.png" };

        for (int i = 0; i < names.length; i++) {
            MediaService mediaService = new MediaService(names[i], images[i]);
            mediaServiceRepository.save(mediaService);
        }
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(DataInitializer o) {
        return this.priority - o.getPriority();
    }
}
