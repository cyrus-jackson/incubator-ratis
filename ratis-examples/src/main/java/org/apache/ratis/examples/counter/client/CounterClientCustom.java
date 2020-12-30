package org.apache.ratis.examples.counter.client;

import org.apache.ratis.client.RaftClient;
import org.apache.ratis.conf.Parameters;
import org.apache.ratis.conf.RaftProperties;
import org.apache.ratis.examples.counter.CounterCommonCustom;
import org.apache.ratis.grpc.GrpcFactory;
import org.apache.ratis.protocol.ClientId;
import org.apache.ratis.protocol.Message;
import org.apache.ratis.protocol.RaftClientReply;
import org.apache.ratis.protocol.RaftPeerId;
import org.apache.ratis.server.RaftServerConfigKeys;

import java.io.IOException;
import java.nio.charset.Charset;
/**
 * Counter client application, this application sends specific number of
 * INCREMENT command to the Counter cluster and at the end sends a GET command
 * and print the result
 * <p>
 * Parameter to this application indicate the number of INCREMENT command, if no
 * parameter found, application use default value which is 10
 */
public final class CounterClientCustom {

  private CounterClientCustom(){
  }

  public static void main(String[] args)
      throws IOException, InterruptedException {
    //indicate the number of INCREMENT command, set 10 if no parameter passed
    int increment = args.length > 0 ? Integer.parseInt(args[0]) : 10;

    //build the counter cluster client
    RaftClient raftClient = buildClient();

    try {
      for(int i = 0; i < 100; i++)
        raftClient.sendAsync(Message.valueOf("INCREMENT"));

//      raftClient.groupAdd(CounterCommonCustom.RAFT_GROUP2, RaftPeerId.getRaftPeerId("n2"));
//      raftClient.groupRemove(CounterCommonCustom.RAFT_GROUP2.getGroupId(), false, true, RaftPeerId.getRaftPeerId("n2"));
    } finally {
      //send GET command and print the response
      RaftClientReply count = raftClient.sendReadOnly(Message.valueOf("GET"));
      String response =
          count.getMessage().getContent().toString(Charset.defaultCharset());
      System.out.println(response);
    }
  }

  /**
   * build the RaftClient instance which is used to communicate to
   * Counter cluster
   *
   * @return the created client of Counter cluster
   */
  private static RaftClient buildClient() {
    RaftProperties raftProperties = new RaftProperties();


    raftProperties.set("raft.server.move.removed.groups.enabled", "true");
    RaftClient.Builder builder = RaftClient.newBuilder()
        .setProperties(raftProperties)
        .setRaftGroup(CounterCommonCustom.RAFT_GROUP1)
        .setClientRpc(
            new GrpcFactory(new Parameters())
                .newRaftClientRpc(ClientId.randomId(), raftProperties));
    return builder.build();
  }
}
