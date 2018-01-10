package vn.nct.cardgame.profile.thriftclient;

import com.bliss.framework.common.Config;
import com.bliss.framework.common.LogUtil;
import com.bliss.framework.util.ConvertUtils;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import vn.nct.cardgame.profile.thrift.TCheckNameResult;
import vn.nct.cardgame.profile.thrift.TGuestResult;
import vn.nct.cardgame.profile.thrift.TPaymentType;
import vn.nct.cardgame.profile.thrift.TProfile;
import vn.nct.cardgame.profile.thrift.TProfileLink;
import vn.nct.cardgame.profile.thrift.TProfileResult;
import vn.nct.cardgame.profile.thrift.TPromotion;
import vn.nct.cardgame.profile.thrift.TPromotionResult;
import vn.nct.cardgame.profile.thrift.TUserPromotionList;
import vn.nct.cardgame.profile.thrift.TVip;
import vn.nct.cardgame.profile.thrift.TVipBenefit;
import vn.nct.cardgame.profile.thrift.TVipBenefitResult;
import vn.nct.cardgame.profile.thrift.TVipResult;

/**
 *
 * @author ThanhNT
 */
public class TProfileClient {

    private static Logger logger = LogUtil.getLogger(TProfileClient.class);
    private static final long maxRecyleAge = 5 * 60 * 1000; // 5 minutes
    private static Map<String, TProfileClient> instances = new NonBlockingHashMap();
    private static final Lock createLock = new ReentrantLock();
    private ArrayBlockingQueue<PooledClient> queue;
    private Integer maxPool;
    private Integer minPool;
    private String host;
    private Integer port;

    public static TProfileClient getInstance(String name) {
        if (!instances.containsKey(name)) {
            try {
                createLock.lock();
                if (!instances.containsKey(name)) {
                    instances.put(name, new TProfileClient(name));
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            } finally {
                createLock.unlock();
            }
        }
        return instances.get(name);
    }

    public TProfileClient(String name) {
        try {
            host = Config.getParam(name, "host");
            port = ConvertUtils.toInt(Config.getParam(name, "port"), 0);

            maxPool = ConvertUtils.toInt(Config.getParam(name, "maxpool"), 1024);
            minPool = ConvertUtils.toInt(Config.getParam(name, "minpool"), 256);

            queue = new ArrayBlockingQueue(maxPool);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private PooledClient borrowClient() {
        PooledClient pooledClient = null;
        while (queue.size() > 0) {
            try {
                pooledClient = queue.take();
            } catch (InterruptedException ex) {
                logger.error(LogUtil.stackTrace(ex));
            }

            if (pooledClient.isAlive()) {
                return pooledClient;
            }
        }

        pooledClient = new PooledClient(host, port);

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
            logger.error(LogUtil.stackTrace(ex));
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

    public boolean insert(TProfile value) {
        boolean result = false;
        PooledClient client = borrowClient();
        try {
            result = client.getClient().insert(value);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updateMoney(int userId, long money) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateMoney(userId, money);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updateAvatar(int userId, String avatarPath) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateAvatar(userId, avatarPath);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updateLastLogin(int userId, String deviceSource) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateLastLogin(userId, deviceSource);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updateDisplayName(int userId, String displayName) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateDisplayName(userId, displayName);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updateDeviceToken(TProfile item) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateDeviceToken(item);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean removeDeviceToken(TProfile item) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().removeDeviceToken(item);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean checkTokenPushNotification(String token, String deviceOS) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().checkTokenPushNotification(token, deviceOS);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean insertTokenPushNotification(String token, int source, String ipAddress, long userId, String username, String deviceOS) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().insertTokenPushNotification(token, source, ipAddress, userId, username, deviceOS);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updateTokenPushNotification(String token, int source, String ipAddress, long userId, String username, String deviceOS) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateTokenPushNotification(token, source, ipAddress, userId, username, deviceOS);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TProfile getByUserId(int userId, boolean isCache) {
        TProfile result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getByUserId(userId, isCache);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TProfile();
        }

        return result;
    }

    public TProfile getByUserName(String username, boolean isCache) {
        TProfile result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getByUserName(username, isCache);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TProfile();
        }

        return result;
    }

    public TProfile getByDisplayName(String displayName, boolean isCache) {
        TProfile result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getByUserDisplayName(displayName, isCache);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TProfile();
        }

        return result;
    }

    public TProfile getByProfileId(String profileId, boolean isCache) {
        TProfile result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getByProfileId(profileId, isCache);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TProfile();
        }

        return result;
    }

    public boolean checkDisplayName(String displayName, boolean isCache) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().checkDisplayName(displayName, isCache);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TProfileResult searchListPaging(String keyword, int pageIndex, int pageSize) {
        TProfileResult result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().searchListPaging(keyword, pageIndex, pageSize);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TProfileResult();
        }

        return result;
    }

    public boolean insertLogErrorToDatabase(int userId, String username, String msg) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().insertLogErrorToDatabase(userId, username, msg);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public int checkDevice(String device, int userId) {
        int result = 0;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().checkDevice(device, userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean removeDevice(String device, int userId) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().removeDevice(device, userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TVipResult getListExpired() {
        TVipResult result = new TVipResult();
        result.listData = new ArrayList<>();
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getListExpired();
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result.error = 1;
            result.listData = new ArrayList<>();
        }

        return result;
    }

    public TVipResult getListOverExpired() {
        TVipResult result = new TVipResult();
        result.listData = new ArrayList<>();
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getListOverExpired();
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result.error = 1;
            result.listData = new ArrayList<>();
        }

        return result;
    }

    public boolean updatePoint(long userId, int point) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updatePoint(userId, point);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updatePointPayment(long userId, int point, TPaymentType type) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updatePointPayment(userId, point, type);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public long calcNcoinToRenew(int userId) {
        long result = -1;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().calcNcoinToRenew(userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TVip getVip(int userId) {
        TVip result = new TVip();
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getVip(userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean kickVip(TVip value) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().kickVip(value);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean deActiveVip(TVip value) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().deActiveVip(value);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean addVipBenefit(TVipBenefit value) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().addVipBenefit(value);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updateVipBenefit(TVipBenefit value) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateVipBenefit(value);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TVipBenefit getVipBenefitAdmin(int id) {
        TVipBenefit result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getVipBenefitAdmin(id);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TVipBenefit(-1, "", "", -1, -1, true);
        }

        return result;
    }

    public TVipBenefitResult getVipBenefitListAdmin() {
        TVipBenefitResult result = new TVipBenefitResult();
        result.listData = new ArrayList<>();
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getVipBenefitListAdmin();
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result.error = 1;
            result.listData = new ArrayList<>();
        }

        return result;
    }

    public TVipBenefitResult getVipBenefitList(int vip) {
        TVipBenefitResult result = new TVipBenefitResult();
        result.listData = new ArrayList<>();
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getVipBenefitList(vip);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result.error = 1;
            result.listData = new ArrayList<>();
        }

        return result;
    }

    public TVipResult getListLocked() {
        TVipResult result = new TVipResult();
        result.listData = new ArrayList<>();
        PooledClient client = borrowClient();
        try {
            result = client.getClient().getListLocked();
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result.error = 1;
            result.listData = new ArrayList<>();
        }

        return result;
    }

    public boolean setLockVip(int userId, boolean lock) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().setLockVip(userId, lock);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TVipResult searchVipListPaging(int userId, int vip, int pageIndex, int pageSize) {
        TVipResult result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().searchVipListPaging(userId, vip, pageIndex, pageSize);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TVipResult();
            result.error = 1;
            result.listData = new ArrayList<>();
            result.totalPage = 0;
            result.totalRecord = 0;
        }

        return result;
    }

    public boolean addPromotion(TPromotion value) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().addPromotion(value);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updatePromotion(TPromotion value) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updatePromotion(value);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public boolean updatePromotionStatus(int userId, int id, short status) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updatePromotionStatus(userId, id, status);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TPromotionResult searchPromotion(int pageIndex, int pageSize) {
        TPromotionResult result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().searchPromotion(pageIndex, pageSize);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TPromotionResult();
            result.error = 1;
            result.listData = new ArrayList<>();
            result.totalPage = 0;
            result.totalRecord = 0;
        }

        return result;
    }

    public TPromotionResult searchPromotionStat(int pageIndex, int pageSize, String from_date, String to_date) {
        TPromotionResult result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().searchPromotionStat(pageIndex, pageSize, from_date, to_date);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TPromotionResult();
            result.error = 1;
            result.listData = new ArrayList<>();
            result.totalPage = 0;
            result.totalRecord = 0;
        }

        return result;
    }

    public TPromotion getPromotion(int userId, int id) {
        TPromotion result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getPromotion(userId, id);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TPromotion(-1, 0, "", 0, (short) 0, 0, "", "", "", "", "", "");
        }

        return result;
    }

    public boolean delPromotion(int userId, int id) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().delPromotion(userId, id);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }

        return result;
    }

    public TUserPromotionList getUserPromotion(int userId) {
        TUserPromotionList result = new TUserPromotionList();
        result.mapData = new ConcurrentHashMap<>();
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getUserPromotion(userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }
        return result;
    }

    public TVipResult searchVipListPagingByType(int vip, int type, int pageIndex, int pageSize) {
        TVipResult result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().searchVipListPagingByType(vip, type, pageIndex, pageSize);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TVipResult();
            result.error = 1;
            result.listData = new ArrayList<>();
            result.totalPage = 0;
            result.totalRecord = 0;
        }

        return result;
    }

    public TProfile getByDeviceId(String deviceId, boolean isCache) {
        TProfile result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getByDeviceId(deviceId, isCache);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TProfile();
        }

        return result;
    }

    public TProfileLink getProfileLink(long userId, boolean isCache) {
        TProfileLink result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getProfileLink(userId, isCache);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = new TProfileLink();
        }

        return result;
    }

    public TGuestResult setProfileLink(TProfileLink profileLink) {
        TGuestResult result;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().setProfileLink(profileLink);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = TGuestResult.SYS_ERROR;
        }

        return result;
    }

    public int getCheckLoginByUserId(String deviceId, int userId) {
        int result = 0;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getCheckLoginByUserId(deviceId, userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = -1;
        }

        return result;

    }

    public int getCheckLoginByDeviceId(String deviceId, int userId) {
        int result = 0;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getCheckLoginByDeviceId(deviceId, userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = -1;
        }

        return result;

    }

    public int getCheckLoginByUserIdWithLimit(String deviceId, int userId, int limit) {
        int result = 0;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getCheckLoginByUserIdWithLimit(deviceId, userId, limit);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = -1;
        }

        return result;

    }

    public int getCheckLoginByDeviceIdWithLimit(String deviceId, int userId, int limit) {
        int result = 0;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getCheckLoginByDeviceIdWithLimit(deviceId, userId, limit);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = -1;
        }

        return result;

    }

    public boolean removeLoginDeviceWithUserId(String deviceId, int userId) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().removeDeviceIdWithUserId(deviceId, userId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = false;
        }

        return result;

    }
    
    public boolean updateLastLoginUserIdDeviceId(String deviceId, int userId) {
        boolean result = false;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().updateLastLoginUserIdDeviceId(userId, deviceId);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
            result = false;
        }
        return result;
    }
    
    public int getMaxUserId() {
        int result = 1000000000;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().getMaxUserId();
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }
        return result;
    }
    
    public TCheckNameResult checkDisplayNameV2(String name) {
        TCheckNameResult result = null;
        PooledClient client = borrowClient();

        try {
            result = client.getClient().checkDisplayNameV2(name);
            returnClient(client);
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            invalidClient(client);
        }
        return result;
    }
    
}
