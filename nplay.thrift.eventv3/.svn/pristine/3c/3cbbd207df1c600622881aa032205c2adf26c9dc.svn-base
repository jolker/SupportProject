package com.bliss.nplay.service.eventv3.thrift.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.cliffc.high_scale_lib.NonBlockingHashMap;

import com.bliss.framework.common.Config;
import com.bliss.framework.util.ConvertUtils;
import com.bliss.nplay.service.eventv3.thrift.EEventStatus;
import com.bliss.nplay.service.eventv3.thrift.TEventV3Value;
import com.bliss.service.thrift.OpAuth;
import com.bliss.service.thrift.TUserMiniProfile;

import ga.log4j.GA;

public class TEventV3ThriftClient {

    private final Integer maxPool;
    private final Integer minPool;
    private final String host;
    private final Integer port;
    private final int timeOut;

    private static final String TAG = TEventV3ThriftClient.class.getName();

    private static final Lock createLock = new ReentrantLock();
    private final ArrayBlockingQueue<PooledClient> queue;
    private static final long maxRecyleAge = 5 * 60 * 1000;
    private static final Map<String, TEventV3ThriftClient> instances = new NonBlockingHashMap<String, TEventV3ThriftClient>();

    public static TEventV3ThriftClient getInstance(String name) {
        if (!instances.containsKey(name)) {
            try {
                createLock.lock();
                if (!instances.containsKey(name)) {
                    instances.put(name, new TEventV3ThriftClient(name));
                }
            } finally {
                createLock.unlock();
            }
        }
        return instances.get(name);
    }

    public TEventV3ThriftClient(String name) {
        host = Config.getParam(name, "host");
        port = ConvertUtils.toInt(Config.getParam(name, "port"), 9669);
        maxPool = ConvertUtils.toInt(Config.getParam(name, "maxpool"), 1024);
        minPool = ConvertUtils.toInt(Config.getParam(name, "minpool"), 16);
        timeOut = ConvertUtils.toInt(Config.getParam(name, "time_out"), 60000);

        queue = new ArrayBlockingQueue<>(maxPool);
    }

    private PooledClient borrowClient() {
        PooledClient pooledClient = null;
        while (queue.size() > 0) {
            try {
                pooledClient = queue.take();
            } catch (InterruptedException ex) {

            }
            if (pooledClient.isAlive()) {
                return pooledClient;
            }
        }
        pooledClient = new PooledClient(host, port, timeOut);
        return pooledClient;
    }

    private void returnClient(PooledClient client) {
        if (client == null) {
            return;
        }
        if (queue.size() >= maxPool) {
            client.destroy();
            return;
        }

        long diffInSec = (System.currentTimeMillis() - client.getDateCreated());

        if (queue.size() > minPool && diffInSec > maxRecyleAge) {
            client.destroy();
            return;
        }
        try {
            queue.put(client);
        } catch (InterruptedException ex) {
        }
    }

    private void invalidClient(PooledClient client) {
        if (client == null) {
            return;
        }
        if (client.isAlive()) {
            returnClient(client);
        }
    }

    public boolean shutdown(OpAuth auth) {
        PooledClient client = borrowClient();
        boolean result = false;
        try {
            result = client.getClient().shutdown(auth);
            returnClient(client);
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return result;
    }

    public boolean reloadConfig() {
        PooledClient client = borrowClient();
        boolean result = false;
        try {
            result = client.getClient().reloadConfig();
            returnClient(client);
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return result;
    }

    public boolean ping() {
        PooledClient client = borrowClient();
        boolean result = false;
        try {
            result = client.getClient().ping();
            returnClient(client);
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return result;
    }

    public List<TEventV3Value> getBanner(TUserMiniProfile userProfile) {
        PooledClient client = borrowClient();
        List<TEventV3Value> listEvent = new ArrayList<>();
        try {
            listEvent = client.getClient().getBanner(userProfile);
            returnClient(client);
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return listEvent;
    }

    public List<TEventV3Value> getPopup(TUserMiniProfile userProfile) {
        PooledClient client = borrowClient();
        List<TEventV3Value> listEvent = new ArrayList<>();
        try {
            listEvent = client.getClient().getPopup(userProfile);
            returnClient(client);
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return listEvent;
    }

    public TEventV3Value getEvent(int eventId) {
        PooledClient client = borrowClient();
        TEventV3Value tEvent = new TEventV3Value();
        try {
            tEvent = client.getClient().getEvent(eventId);
            returnClient(client);

        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return tEvent;
    }

    public List<TEventV3Value> getAllEvent(long dateBegin, long dateEnd, short page, short offset) {
        PooledClient client = borrowClient();
        List<TEventV3Value> listEvent= new ArrayList<>();
        try {

            listEvent = client.getClient().getAllEvent(dateBegin, dateEnd, page, offset);
            returnClient(client);

        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
            
        }
        return listEvent;
    }

    public TEventV3Value addEvent(OpAuth auth, TEventV3Value event) {
        PooledClient client = borrowClient();
        try {
            event = client.getClient().addEvent(auth, event);
            returnClient(client);            
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
            // luu that bai
            event.eventId = 0;
        }
        return event;
    }

    public boolean updateEvent(OpAuth auth, TEventV3Value event) {
        PooledClient client = borrowClient();
        boolean result = false;
        try {
            result = client.getClient().updateEvent(auth, event);
            returnClient(client);
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return result;
    }

    public boolean updateStatus(OpAuth auth, int eventID, EEventStatus status) {
        PooledClient client = borrowClient();
        boolean result = false;
        try {
            result = client.getClient().updateStatus(auth, eventID, status);
            returnClient(client);
        } catch (Exception ex) {
            GA.app.error("Error Module : " + TAG + " with message " + ex.getMessage(), ex);
            invalidClient(client);
        }
        return result;
    }

}
