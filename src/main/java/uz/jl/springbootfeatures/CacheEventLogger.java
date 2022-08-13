package uz.jl.springbootfeatures;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
 * @author "Elmurodov Javohir"
 * @since 13/08/22/13:00 (Saturday)
 * spring-boot-features/IntelliJ IDEA
 */
@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {
    @Override
    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        System.out.println("Cache event listener => " + cacheEvent.getKey() + cacheEvent.getOldValue() + cacheEvent.getNewValue());
    }
}