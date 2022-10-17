package com.qwe910205.plusdotcom.datainit.service.initializer;

import com.qwe910205.plusdotcom.plan.domain.MediaService;
import com.qwe910205.plusdotcom.plan.domain.Plan;
import com.qwe910205.plusdotcom.plan.domain.PremiumService;
import com.qwe910205.plusdotcom.plan.domain.wrapper.PlanName;
import com.qwe910205.plusdotcom.plan.domain.wrapper.ServiceName;
import com.qwe910205.plusdotcom.plan.repository.MediaServiceRepository;
import com.qwe910205.plusdotcom.plan.repository.PlanRepository;
import com.qwe910205.plusdotcom.plan.repository.PremiumServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class PlanServiceInitializer implements DataInitializer {

    private int priority = 2;
    private final PlanRepository planRepository;
    private final PremiumServiceRepository premiumServiceRepository;
    private final MediaServiceRepository mediaServiceRepository;

    @Override
    public void init() {
        addPremiumServiceToPlan();
        addMediaServiceToPlan();
    }

    private void addPremiumServiceToPlan() {
        insertPremiumService("5G 시그니처", "넷플릭스+유튜브 프리미엄", "티빙", "디즈니+", "넷플릭스", "유튜브 프리미엄", "헬로렌탈 구독", "일리커피 구독", "구글패키지", "초등나라",
                "갤럭시 버즈", "갤럭시 버즈2 프로", "갤럭시 워치5");
        insertPremiumService("5G 프리미어 슈퍼", "넷플릭스+유튜브 프리미엄", "티빙", "디즈니+", "넷플릭스", "유튜브 프리미엄", "헬로렌탈 구독", "일리커피 구독", "구글패키지", "초등나라",
                "갤럭시 버즈", "갤럭시 버즈2 프로", "갤럭시 워치5");
        insertPremiumService("5G 프리미어 플러스", "디즈니+", "티빙", "넷플릭스", "유튜브 프리미엄", "헬로렌탈 구독", "일리커피 구독", "구글패키지", "초등나라", "갤럭시 버즈", "갤럭시 버즈2 프로");
        insertPremiumService("5G 프리미어 레귤러", "디즈니+", "티빙");
        insertPremiumService("LTE 프리미어 플러스", "디즈니+", "티빙", "유튜브 프리미엄", "헬로렌탈 구독", "일리커피 구독", "구글패키지", "초등나라");
    }

    private void insertPremiumService(String planName, String... premiumServiceNames) {
        Plan plan = planRepository.findByName(new PlanName(planName))
                .orElseThrow(() -> new IllegalArgumentException("요금제 이름이 " + planName + "인 요금제는 존재하지 않습니다."));
        for (String premiumServiceName : premiumServiceNames) {
            PremiumService premiumService = premiumServiceRepository.findById(new ServiceName(premiumServiceName)).orElseThrow(IllegalArgumentException::new);
            plan.addPremiumService(premiumService);
        }
    }

    private void addMediaServiceToPlan() {
        insertMediaService("5G 시그니처", "지니뮤직 앱", "U+영화월정액(구 영화월정액)", "U+아이들 생생도서관", "밀리의 서재");
        insertMediaService("5G 프리미어 슈퍼", "지니뮤직 앱", "U+영화월정액(구 영화월정액)", "U+아이들 생생도서관", "밀리의 서재");
        insertMediaService("5G 프리미어 플러스", "지니뮤직 앱", "U+영화월정액(구 영화월정액)", "U+아이들 생생도서관", "밀리의 서재");
        insertMediaService("5G 프리미어 레귤러", "지니뮤직 앱", "U+영화월정액(구 영화월정액)", "U+아이들 생생도서관", "밀리의 서재");
        insertMediaService("LTE 프리미어 플러스", "지니뮤직 앱", "U+영화월정액(구 영화월정액)", "U+아이들 생생도서관", "밀리의 서재");
    }

    private void insertMediaService(String planName, String... mediaServiceNames) {
        Plan plan = planRepository.findByName(new PlanName(planName))
                .orElseThrow(() -> new IllegalArgumentException("요금제 이름이 " + planName + "인 요금제는 존재하지 않습니다."));
        for (String mediaServiceName : mediaServiceNames) {
            MediaService mediaService = mediaServiceRepository.findById(new ServiceName(mediaServiceName)).orElseThrow(IllegalArgumentException::new);
            plan.addMediaService(mediaService);
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
