/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ratis.examples.counter;

import org.apache.ratis.protocol.RaftGroup;
import org.apache.ratis.protocol.RaftGroupId;
import org.apache.ratis.protocol.RaftPeer;
import org.apache.ratis.protocol.RaftPeerId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Common Constant across servers and client
 */
public final class CounterCommonCustom {
  public static final List<RaftPeer> PEERS1 = new ArrayList<>(3);
  public static final List<RaftPeer> PEERS2 = new ArrayList<>(3);

  static {
    PEERS2.add(new RaftPeer(RaftPeerId.getRaftPeerId("n1"), "127.0.0.1:6000"));
    PEERS1.add(new RaftPeer(RaftPeerId.getRaftPeerId("n2"), "127.0.0.1:6001"));
    PEERS1.add(new RaftPeer(RaftPeerId.getRaftPeerId("n3"), "127.0.0.1:6002"));
    PEERS2.add(new RaftPeer(RaftPeerId.getRaftPeerId("n3"), "127.0.0.1:6002"));
    PEERS1.add(new RaftPeer(RaftPeerId.getRaftPeerId("n4"), "127.0.0.1:6003"));
    PEERS2.add(new RaftPeer(RaftPeerId.getRaftPeerId("n4"), "127.0.0.1:6003"));
  }

  private CounterCommonCustom() {
  }

  private static final UUID CLUSTER_GROUP_ID1 = UUID.fromString("02511d47-d67c-49a3-9011-abb3109a44c1");
  public static final RaftGroup RAFT_GROUP1 = RaftGroup.valueOf(
      RaftGroupId.valueOf(CounterCommonCustom.CLUSTER_GROUP_ID1), PEERS1);
  private static final UUID CLUSTER_GROUP_ID2 = UUID.fromString("d7477f16-c1cf-11ea-b3de-0242ac130004");
  public static final RaftGroup RAFT_GROUP2 = RaftGroup.valueOf(
      RaftGroupId.valueOf(CounterCommonCustom.CLUSTER_GROUP_ID2), PEERS2);
}
