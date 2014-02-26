/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tangosol.examples.contacts;

import com.tangosol.examples.pof.Pm;
import com.tangosol.examples.pof.Target;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import com.tangosol.util.filter.LikeFilter;
import com.tangosol.util.filter.MapEventFilter;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author handasa
 */
public class MyTest {

    public static void main(String[] args) throws InterruptedException {
        final NamedCache cachePm = CacheFactory.getCache("pm");
        final NamedCache cacheTarget = CacheFactory.getCache("target");
        ScheduledExecutorService exec = new ScheduledThreadPoolExecutor(1);
        cachePm.clear();
        cacheTarget.clear();
        
        registerListener(cachePm);
        initCaches(cacheTarget, cachePm);
        scheduleUpdates(exec, cachePm, cacheTarget);
        scheduleDeletes(exec, cachePm);

        Thread.sleep(10000);
        exec.shutdownNow();
        CacheFactory.shutdown();
    }

    private static void scheduleDeletes(ScheduledExecutorService exec, final NamedCache cachePm) {
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                Set set = cachePm.keySet();
                int skip = random.nextInt(set.size());
                Object object = null;
                for (Iterator it = set.iterator(); it.hasNext();) {
                    object = it.next();
                    if(--skip==0)
                        break;                    
                }
                cachePm.remove(object);
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    private static void scheduleUpdates(ScheduledExecutorService exec, final NamedCache cachePm, final NamedCache cacheTarget) {
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                Pm pm = generateRandomPm(random, random.nextInt(MAX_PM), new Date());
                cachePm.put((int)pm.getId(), pm);
                Target target = generateRandomTarget(random, random.nextInt(MAX_TARGETS));
                cacheTarget.put((int)target.getId(), target);
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    private static void registerListener(final NamedCache cachePm) {
        cachePm.addMapListener(createListener(new Function<MapEvent, Void>()  {
            @Override
            public Void apply(MapEvent t) {
                System.out.println(t);
                return null;
            }
        }),new MapEventFilter(MapEventFilter.E_KEYSET,new LikeFilter("getName","a%")),false);
    }

    private static void initCaches(NamedCache cacheTarget, NamedCache cachePm) {
        Date now = new Date();
        Random random = new Random();
        for (int i = 0; i < MAX_TARGETS; i++) {
            cacheTarget.put(i, generateRandomTarget(random, i));
        }

        for (int i = 0; i < MAX_PM; i++) {
            cachePm.put(i, generateRandomPm(random, i, now));
        }
    }

    private static Pm generateRandomPm(Random random, int i, Date now) {
        int[] fields = new int[TARGET_FIELDS];
        for (int j = 0; j < TARGET_FIELDS; j++) {
            fields[j] = random.nextInt();
        }
        Pm pm = new Pm(i, generateRandomWords(random), fields, random.nextInt(MAX_TARGETS), generateRandomDate(random, now));
        return pm;
    }

    private static Target generateRandomTarget(Random random, int id) {
        int[] fields = new int[TARGET_FIELDS];
        for (int j = 0; j < TARGET_FIELDS; j++) {
            fields[j] = random.nextInt();
        }
        Target target = new Target(id, fields, generateRandomWords(random));
        return target;
    }
    public static final int MAX_TARGETS = 1000;
    public static final int MAX_PM = 10000;
    public static final int TARGET_FIELDS = 20;
    public static final int PM_FIELDS = 20;

    public static String generateRandomWords(Random random) {
        char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }

    public static Date generateRandomDate(Random random, Date now) {
        return new Date(now.getTime() + random.nextInt(24 * 365) * 3600l * 1000l);
    }

    public interface Function<T,R> {
        public R apply(T t);    
    }

    public static MapListener createListener(final Function<MapEvent,Void> f){
        return new MapListener() {
            @Override
            public void entryInserted(MapEvent me) {
                f.apply(me);         
            }

            @Override
            public void entryUpdated(MapEvent me) {
                f.apply(me);         
            }

            @Override
            public void entryDeleted(MapEvent me) {
                f.apply(me);         
            }
        };        
    }
}
