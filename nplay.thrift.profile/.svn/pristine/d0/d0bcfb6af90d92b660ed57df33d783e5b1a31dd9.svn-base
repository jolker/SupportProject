package vn.nct.cardgame.profile.thriftclient;

import com.bliss.framework.common.LogUtil;
import org.apache.log4j.Logger;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import vn.nct.cardgame.profile.thrift.TProfileService;

/**
 *
 * @author ThanhNT
 */
public class PooledClient {

    private static Logger logger = LogUtil.getLogger(PooledClient.class);
    private TProfileService.Client client;
    private long dateCreated;

    public PooledClient(String host, int port) {

        dateCreated = System.currentTimeMillis();

        TTransport transport = new TFramedTransport(new TSocket(host, port));
        TProtocol protocol = new TBinaryProtocol(transport);
        try {
            transport.open();
            TServiceClientFactory factory = new TProfileService.Client.Factory();
            client = (TProfileService.Client) factory.getClient(protocol);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    public boolean isAlive() {

        if (client == null) {
            return false;
        }

        TFramedTransport transport = (TFramedTransport) client.getOutputProtocol().getTransport();

        if (transport == null || transport.isOpen() == false) {
            return false;
        }

        try {
            client.ping();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void destroy() {
        if (client == null) {
            return;
        }

        TTransport itrans = client.getInputProtocol().getTransport();
        TTransport otrans = client.getOutputProtocol().getTransport();
        if (itrans != null) {
            itrans.close();
        }
        if (otrans != null && otrans != itrans) {
            otrans.close();
        }
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public TProfileService.Client getClient() {
        return client;
    }
}
