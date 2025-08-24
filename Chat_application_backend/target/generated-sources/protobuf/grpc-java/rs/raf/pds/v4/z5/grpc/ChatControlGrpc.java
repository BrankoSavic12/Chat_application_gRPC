package rs.raf.pds.v4.z5.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.0)",
    comments = "Source: chat_control.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatControlGrpc {

  private ChatControlGrpc() {}

  public static final String SERVICE_NAME = "rs.raf.pds.v4.z5.grpc.ChatControl";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.CreateRoomRequest,
      rs.raf.pds.v4.z5.grpc.Room> getCreateRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createRoom",
      requestType = rs.raf.pds.v4.z5.grpc.CreateRoomRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.Room.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.CreateRoomRequest,
      rs.raf.pds.v4.z5.grpc.Room> getCreateRoomMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.CreateRoomRequest, rs.raf.pds.v4.z5.grpc.Room> getCreateRoomMethod;
    if ((getCreateRoomMethod = ChatControlGrpc.getCreateRoomMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getCreateRoomMethod = ChatControlGrpc.getCreateRoomMethod) == null) {
          ChatControlGrpc.getCreateRoomMethod = getCreateRoomMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.CreateRoomRequest, rs.raf.pds.v4.z5.grpc.Room>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.CreateRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.Room.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("createRoom"))
              .build();
        }
      }
    }
    return getCreateRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.InviteUsersRequest,
      rs.raf.pds.v4.z5.grpc.Room> getInviteUsersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "inviteUsers",
      requestType = rs.raf.pds.v4.z5.grpc.InviteUsersRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.Room.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.InviteUsersRequest,
      rs.raf.pds.v4.z5.grpc.Room> getInviteUsersMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.InviteUsersRequest, rs.raf.pds.v4.z5.grpc.Room> getInviteUsersMethod;
    if ((getInviteUsersMethod = ChatControlGrpc.getInviteUsersMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getInviteUsersMethod = ChatControlGrpc.getInviteUsersMethod) == null) {
          ChatControlGrpc.getInviteUsersMethod = getInviteUsersMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.InviteUsersRequest, rs.raf.pds.v4.z5.grpc.Room>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "inviteUsers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.InviteUsersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.Room.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("inviteUsers"))
              .build();
        }
      }
    }
    return getInviteUsersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.ListRoomsRequest,
      rs.raf.pds.v4.z5.grpc.Room> getListRoomsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listRooms",
      requestType = rs.raf.pds.v4.z5.grpc.ListRoomsRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.Room.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.ListRoomsRequest,
      rs.raf.pds.v4.z5.grpc.Room> getListRoomsMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.ListRoomsRequest, rs.raf.pds.v4.z5.grpc.Room> getListRoomsMethod;
    if ((getListRoomsMethod = ChatControlGrpc.getListRoomsMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getListRoomsMethod = ChatControlGrpc.getListRoomsMethod) == null) {
          ChatControlGrpc.getListRoomsMethod = getListRoomsMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.ListRoomsRequest, rs.raf.pds.v4.z5.grpc.Room>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listRooms"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.ListRoomsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.Room.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("listRooms"))
              .build();
        }
      }
    }
    return getListRoomsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.JoinRoomRequest,
      rs.raf.pds.v4.z5.grpc.JoinRoomResponse> getJoinRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "joinRoom",
      requestType = rs.raf.pds.v4.z5.grpc.JoinRoomRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.JoinRoomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.JoinRoomRequest,
      rs.raf.pds.v4.z5.grpc.JoinRoomResponse> getJoinRoomMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.JoinRoomRequest, rs.raf.pds.v4.z5.grpc.JoinRoomResponse> getJoinRoomMethod;
    if ((getJoinRoomMethod = ChatControlGrpc.getJoinRoomMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getJoinRoomMethod = ChatControlGrpc.getJoinRoomMethod) == null) {
          ChatControlGrpc.getJoinRoomMethod = getJoinRoomMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.JoinRoomRequest, rs.raf.pds.v4.z5.grpc.JoinRoomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "joinRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.JoinRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.JoinRoomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("joinRoom"))
              .build();
        }
      }
    }
    return getJoinRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.MoreRoomRequest,
      rs.raf.pds.v4.z5.grpc.MoreRoomResponse> getGetMoreRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getMoreRoom",
      requestType = rs.raf.pds.v4.z5.grpc.MoreRoomRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.MoreRoomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.MoreRoomRequest,
      rs.raf.pds.v4.z5.grpc.MoreRoomResponse> getGetMoreRoomMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.MoreRoomRequest, rs.raf.pds.v4.z5.grpc.MoreRoomResponse> getGetMoreRoomMethod;
    if ((getGetMoreRoomMethod = ChatControlGrpc.getGetMoreRoomMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getGetMoreRoomMethod = ChatControlGrpc.getGetMoreRoomMethod) == null) {
          ChatControlGrpc.getGetMoreRoomMethod = getGetMoreRoomMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.MoreRoomRequest, rs.raf.pds.v4.z5.grpc.MoreRoomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getMoreRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.MoreRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.MoreRoomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("getMoreRoom"))
              .build();
        }
      }
    }
    return getGetMoreRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.LeaveRoomRequest,
      rs.raf.pds.v4.z5.grpc.LeaveRoomResponse> getLeaveRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "leaveRoom",
      requestType = rs.raf.pds.v4.z5.grpc.LeaveRoomRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.LeaveRoomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.LeaveRoomRequest,
      rs.raf.pds.v4.z5.grpc.LeaveRoomResponse> getLeaveRoomMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.LeaveRoomRequest, rs.raf.pds.v4.z5.grpc.LeaveRoomResponse> getLeaveRoomMethod;
    if ((getLeaveRoomMethod = ChatControlGrpc.getLeaveRoomMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getLeaveRoomMethod = ChatControlGrpc.getLeaveRoomMethod) == null) {
          ChatControlGrpc.getLeaveRoomMethod = getLeaveRoomMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.LeaveRoomRequest, rs.raf.pds.v4.z5.grpc.LeaveRoomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "leaveRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.LeaveRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.LeaveRoomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("leaveRoom"))
              .build();
        }
      }
    }
    return getLeaveRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.DMKey,
      rs.raf.pds.v4.z5.grpc.DMHistoryResponse> getGetLastDMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLastDM",
      requestType = rs.raf.pds.v4.z5.grpc.DMKey.class,
      responseType = rs.raf.pds.v4.z5.grpc.DMHistoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.DMKey,
      rs.raf.pds.v4.z5.grpc.DMHistoryResponse> getGetLastDMMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.DMKey, rs.raf.pds.v4.z5.grpc.DMHistoryResponse> getGetLastDMMethod;
    if ((getGetLastDMMethod = ChatControlGrpc.getGetLastDMMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getGetLastDMMethod = ChatControlGrpc.getGetLastDMMethod) == null) {
          ChatControlGrpc.getGetLastDMMethod = getGetLastDMMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.DMKey, rs.raf.pds.v4.z5.grpc.DMHistoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getLastDM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.DMKey.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.DMHistoryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("getLastDM"))
              .build();
        }
      }
    }
    return getGetLastDMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest,
      rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse> getSendRoomMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendRoomMessage",
      requestType = rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest,
      rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse> getSendRoomMessageMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest, rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse> getSendRoomMessageMethod;
    if ((getSendRoomMessageMethod = ChatControlGrpc.getSendRoomMessageMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getSendRoomMessageMethod = ChatControlGrpc.getSendRoomMessageMethod) == null) {
          ChatControlGrpc.getSendRoomMessageMethod = getSendRoomMessageMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest, rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendRoomMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("sendRoomMessage"))
              .build();
        }
      }
    }
    return getSendRoomMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.EditMessageRequest,
      rs.raf.pds.v4.z5.grpc.EditMessageResponse> getEditMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "editMessage",
      requestType = rs.raf.pds.v4.z5.grpc.EditMessageRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.EditMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.EditMessageRequest,
      rs.raf.pds.v4.z5.grpc.EditMessageResponse> getEditMessageMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.EditMessageRequest, rs.raf.pds.v4.z5.grpc.EditMessageResponse> getEditMessageMethod;
    if ((getEditMessageMethod = ChatControlGrpc.getEditMessageMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getEditMessageMethod = ChatControlGrpc.getEditMessageMethod) == null) {
          ChatControlGrpc.getEditMessageMethod = getEditMessageMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.EditMessageRequest, rs.raf.pds.v4.z5.grpc.EditMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "editMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.EditMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.EditMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("editMessage"))
              .build();
        }
      }
    }
    return getEditMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.SendMulticastRequest,
      rs.raf.pds.v4.z5.grpc.SendMulticastResponse> getSendMulticastMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendMulticast",
      requestType = rs.raf.pds.v4.z5.grpc.SendMulticastRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.SendMulticastResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.SendMulticastRequest,
      rs.raf.pds.v4.z5.grpc.SendMulticastResponse> getSendMulticastMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.SendMulticastRequest, rs.raf.pds.v4.z5.grpc.SendMulticastResponse> getSendMulticastMethod;
    if ((getSendMulticastMethod = ChatControlGrpc.getSendMulticastMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getSendMulticastMethod = ChatControlGrpc.getSendMulticastMethod) == null) {
          ChatControlGrpc.getSendMulticastMethod = getSendMulticastMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.SendMulticastRequest, rs.raf.pds.v4.z5.grpc.SendMulticastResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendMulticast"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.SendMulticastRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.SendMulticastResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("sendMulticast"))
              .build();
        }
      }
    }
    return getSendMulticastMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.RespondInviteRequest,
      rs.raf.pds.v4.z5.grpc.RespondInviteResponse> getRespondInviteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "respondInvite",
      requestType = rs.raf.pds.v4.z5.grpc.RespondInviteRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.RespondInviteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.RespondInviteRequest,
      rs.raf.pds.v4.z5.grpc.RespondInviteResponse> getRespondInviteMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.RespondInviteRequest, rs.raf.pds.v4.z5.grpc.RespondInviteResponse> getRespondInviteMethod;
    if ((getRespondInviteMethod = ChatControlGrpc.getRespondInviteMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getRespondInviteMethod = ChatControlGrpc.getRespondInviteMethod) == null) {
          ChatControlGrpc.getRespondInviteMethod = getRespondInviteMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.RespondInviteRequest, rs.raf.pds.v4.z5.grpc.RespondInviteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "respondInvite"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.RespondInviteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.RespondInviteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("respondInvite"))
              .build();
        }
      }
    }
    return getRespondInviteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.ListInvitesRequest,
      rs.raf.pds.v4.z5.grpc.ListInvitesResponse> getListInvitesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listInvites",
      requestType = rs.raf.pds.v4.z5.grpc.ListInvitesRequest.class,
      responseType = rs.raf.pds.v4.z5.grpc.ListInvitesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.ListInvitesRequest,
      rs.raf.pds.v4.z5.grpc.ListInvitesResponse> getListInvitesMethod() {
    io.grpc.MethodDescriptor<rs.raf.pds.v4.z5.grpc.ListInvitesRequest, rs.raf.pds.v4.z5.grpc.ListInvitesResponse> getListInvitesMethod;
    if ((getListInvitesMethod = ChatControlGrpc.getListInvitesMethod) == null) {
      synchronized (ChatControlGrpc.class) {
        if ((getListInvitesMethod = ChatControlGrpc.getListInvitesMethod) == null) {
          ChatControlGrpc.getListInvitesMethod = getListInvitesMethod =
              io.grpc.MethodDescriptor.<rs.raf.pds.v4.z5.grpc.ListInvitesRequest, rs.raf.pds.v4.z5.grpc.ListInvitesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listInvites"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.ListInvitesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.pds.v4.z5.grpc.ListInvitesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatControlMethodDescriptorSupplier("listInvites"))
              .build();
        }
      }
    }
    return getListInvitesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatControlStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatControlStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatControlStub>() {
        @java.lang.Override
        public ChatControlStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatControlStub(channel, callOptions);
        }
      };
    return ChatControlStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatControlBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatControlBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatControlBlockingStub>() {
        @java.lang.Override
        public ChatControlBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatControlBlockingStub(channel, callOptions);
        }
      };
    return ChatControlBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatControlFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatControlFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatControlFutureStub>() {
        @java.lang.Override
        public ChatControlFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatControlFutureStub(channel, callOptions);
        }
      };
    return ChatControlFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createRoom(rs.raf.pds.v4.z5.grpc.CreateRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRoomMethod(), responseObserver);
    }

    /**
     */
    default void inviteUsers(rs.raf.pds.v4.z5.grpc.InviteUsersRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInviteUsersMethod(), responseObserver);
    }

    /**
     */
    default void listRooms(rs.raf.pds.v4.z5.grpc.ListRoomsRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListRoomsMethod(), responseObserver);
    }

    /**
     */
    default void joinRoom(rs.raf.pds.v4.z5.grpc.JoinRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.JoinRoomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinRoomMethod(), responseObserver);
    }

    /**
     */
    default void getMoreRoom(rs.raf.pds.v4.z5.grpc.MoreRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.MoreRoomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMoreRoomMethod(), responseObserver);
    }

    /**
     */
    default void leaveRoom(rs.raf.pds.v4.z5.grpc.LeaveRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.LeaveRoomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLeaveRoomMethod(), responseObserver);
    }

    /**
     */
    default void getLastDM(rs.raf.pds.v4.z5.grpc.DMKey request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.DMHistoryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLastDMMethod(), responseObserver);
    }

    /**
     */
    default void sendRoomMessage(rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendRoomMessageMethod(), responseObserver);
    }

    /**
     */
    default void editMessage(rs.raf.pds.v4.z5.grpc.EditMessageRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.EditMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEditMessageMethod(), responseObserver);
    }

    /**
     */
    default void sendMulticast(rs.raf.pds.v4.z5.grpc.SendMulticastRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.SendMulticastResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMulticastMethod(), responseObserver);
    }

    /**
     */
    default void respondInvite(rs.raf.pds.v4.z5.grpc.RespondInviteRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.RespondInviteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRespondInviteMethod(), responseObserver);
    }

    /**
     */
    default void listInvites(rs.raf.pds.v4.z5.grpc.ListInvitesRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.ListInvitesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListInvitesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ChatControl.
   */
  public static abstract class ChatControlImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ChatControlGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ChatControl.
   */
  public static final class ChatControlStub
      extends io.grpc.stub.AbstractAsyncStub<ChatControlStub> {
    private ChatControlStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatControlStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatControlStub(channel, callOptions);
    }

    /**
     */
    public void createRoom(rs.raf.pds.v4.z5.grpc.CreateRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void inviteUsers(rs.raf.pds.v4.z5.grpc.InviteUsersRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInviteUsersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listRooms(rs.raf.pds.v4.z5.grpc.ListRoomsRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getListRoomsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void joinRoom(rs.raf.pds.v4.z5.grpc.JoinRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.JoinRoomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMoreRoom(rs.raf.pds.v4.z5.grpc.MoreRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.MoreRoomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMoreRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void leaveRoom(rs.raf.pds.v4.z5.grpc.LeaveRoomRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.LeaveRoomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLeaveRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLastDM(rs.raf.pds.v4.z5.grpc.DMKey request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.DMHistoryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLastDMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendRoomMessage(rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendRoomMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void editMessage(rs.raf.pds.v4.z5.grpc.EditMessageRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.EditMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEditMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendMulticast(rs.raf.pds.v4.z5.grpc.SendMulticastRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.SendMulticastResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMulticastMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void respondInvite(rs.raf.pds.v4.z5.grpc.RespondInviteRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.RespondInviteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRespondInviteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listInvites(rs.raf.pds.v4.z5.grpc.ListInvitesRequest request,
        io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.ListInvitesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListInvitesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ChatControl.
   */
  public static final class ChatControlBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ChatControlBlockingStub> {
    private ChatControlBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatControlBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatControlBlockingStub(channel, callOptions);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.Room createRoom(rs.raf.pds.v4.z5.grpc.CreateRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.Room inviteUsers(rs.raf.pds.v4.z5.grpc.InviteUsersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInviteUsersMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<rs.raf.pds.v4.z5.grpc.Room> listRooms(
        rs.raf.pds.v4.z5.grpc.ListRoomsRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getListRoomsMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.JoinRoomResponse joinRoom(rs.raf.pds.v4.z5.grpc.JoinRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.MoreRoomResponse getMoreRoom(rs.raf.pds.v4.z5.grpc.MoreRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMoreRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.LeaveRoomResponse leaveRoom(rs.raf.pds.v4.z5.grpc.LeaveRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLeaveRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.DMHistoryResponse getLastDM(rs.raf.pds.v4.z5.grpc.DMKey request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLastDMMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse sendRoomMessage(rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendRoomMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.EditMessageResponse editMessage(rs.raf.pds.v4.z5.grpc.EditMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEditMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.SendMulticastResponse sendMulticast(rs.raf.pds.v4.z5.grpc.SendMulticastRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMulticastMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.RespondInviteResponse respondInvite(rs.raf.pds.v4.z5.grpc.RespondInviteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRespondInviteMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.pds.v4.z5.grpc.ListInvitesResponse listInvites(rs.raf.pds.v4.z5.grpc.ListInvitesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListInvitesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ChatControl.
   */
  public static final class ChatControlFutureStub
      extends io.grpc.stub.AbstractFutureStub<ChatControlFutureStub> {
    private ChatControlFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatControlFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatControlFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.Room> createRoom(
        rs.raf.pds.v4.z5.grpc.CreateRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.Room> inviteUsers(
        rs.raf.pds.v4.z5.grpc.InviteUsersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInviteUsersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.JoinRoomResponse> joinRoom(
        rs.raf.pds.v4.z5.grpc.JoinRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.MoreRoomResponse> getMoreRoom(
        rs.raf.pds.v4.z5.grpc.MoreRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMoreRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.LeaveRoomResponse> leaveRoom(
        rs.raf.pds.v4.z5.grpc.LeaveRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLeaveRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.DMHistoryResponse> getLastDM(
        rs.raf.pds.v4.z5.grpc.DMKey request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLastDMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse> sendRoomMessage(
        rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendRoomMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.EditMessageResponse> editMessage(
        rs.raf.pds.v4.z5.grpc.EditMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEditMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.SendMulticastResponse> sendMulticast(
        rs.raf.pds.v4.z5.grpc.SendMulticastRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMulticastMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.RespondInviteResponse> respondInvite(
        rs.raf.pds.v4.z5.grpc.RespondInviteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRespondInviteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.pds.v4.z5.grpc.ListInvitesResponse> listInvites(
        rs.raf.pds.v4.z5.grpc.ListInvitesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListInvitesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_ROOM = 0;
  private static final int METHODID_INVITE_USERS = 1;
  private static final int METHODID_LIST_ROOMS = 2;
  private static final int METHODID_JOIN_ROOM = 3;
  private static final int METHODID_GET_MORE_ROOM = 4;
  private static final int METHODID_LEAVE_ROOM = 5;
  private static final int METHODID_GET_LAST_DM = 6;
  private static final int METHODID_SEND_ROOM_MESSAGE = 7;
  private static final int METHODID_EDIT_MESSAGE = 8;
  private static final int METHODID_SEND_MULTICAST = 9;
  private static final int METHODID_RESPOND_INVITE = 10;
  private static final int METHODID_LIST_INVITES = 11;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_ROOM:
          serviceImpl.createRoom((rs.raf.pds.v4.z5.grpc.CreateRoomRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room>) responseObserver);
          break;
        case METHODID_INVITE_USERS:
          serviceImpl.inviteUsers((rs.raf.pds.v4.z5.grpc.InviteUsersRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room>) responseObserver);
          break;
        case METHODID_LIST_ROOMS:
          serviceImpl.listRooms((rs.raf.pds.v4.z5.grpc.ListRoomsRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.Room>) responseObserver);
          break;
        case METHODID_JOIN_ROOM:
          serviceImpl.joinRoom((rs.raf.pds.v4.z5.grpc.JoinRoomRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.JoinRoomResponse>) responseObserver);
          break;
        case METHODID_GET_MORE_ROOM:
          serviceImpl.getMoreRoom((rs.raf.pds.v4.z5.grpc.MoreRoomRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.MoreRoomResponse>) responseObserver);
          break;
        case METHODID_LEAVE_ROOM:
          serviceImpl.leaveRoom((rs.raf.pds.v4.z5.grpc.LeaveRoomRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.LeaveRoomResponse>) responseObserver);
          break;
        case METHODID_GET_LAST_DM:
          serviceImpl.getLastDM((rs.raf.pds.v4.z5.grpc.DMKey) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.DMHistoryResponse>) responseObserver);
          break;
        case METHODID_SEND_ROOM_MESSAGE:
          serviceImpl.sendRoomMessage((rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse>) responseObserver);
          break;
        case METHODID_EDIT_MESSAGE:
          serviceImpl.editMessage((rs.raf.pds.v4.z5.grpc.EditMessageRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.EditMessageResponse>) responseObserver);
          break;
        case METHODID_SEND_MULTICAST:
          serviceImpl.sendMulticast((rs.raf.pds.v4.z5.grpc.SendMulticastRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.SendMulticastResponse>) responseObserver);
          break;
        case METHODID_RESPOND_INVITE:
          serviceImpl.respondInvite((rs.raf.pds.v4.z5.grpc.RespondInviteRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.RespondInviteResponse>) responseObserver);
          break;
        case METHODID_LIST_INVITES:
          serviceImpl.listInvites((rs.raf.pds.v4.z5.grpc.ListInvitesRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.pds.v4.z5.grpc.ListInvitesResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.CreateRoomRequest,
              rs.raf.pds.v4.z5.grpc.Room>(
                service, METHODID_CREATE_ROOM)))
        .addMethod(
          getInviteUsersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.InviteUsersRequest,
              rs.raf.pds.v4.z5.grpc.Room>(
                service, METHODID_INVITE_USERS)))
        .addMethod(
          getListRoomsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.ListRoomsRequest,
              rs.raf.pds.v4.z5.grpc.Room>(
                service, METHODID_LIST_ROOMS)))
        .addMethod(
          getJoinRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.JoinRoomRequest,
              rs.raf.pds.v4.z5.grpc.JoinRoomResponse>(
                service, METHODID_JOIN_ROOM)))
        .addMethod(
          getGetMoreRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.MoreRoomRequest,
              rs.raf.pds.v4.z5.grpc.MoreRoomResponse>(
                service, METHODID_GET_MORE_ROOM)))
        .addMethod(
          getLeaveRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.LeaveRoomRequest,
              rs.raf.pds.v4.z5.grpc.LeaveRoomResponse>(
                service, METHODID_LEAVE_ROOM)))
        .addMethod(
          getGetLastDMMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.DMKey,
              rs.raf.pds.v4.z5.grpc.DMHistoryResponse>(
                service, METHODID_GET_LAST_DM)))
        .addMethod(
          getSendRoomMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.SendRoomMessageRequest,
              rs.raf.pds.v4.z5.grpc.SendRoomMessageResponse>(
                service, METHODID_SEND_ROOM_MESSAGE)))
        .addMethod(
          getEditMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.EditMessageRequest,
              rs.raf.pds.v4.z5.grpc.EditMessageResponse>(
                service, METHODID_EDIT_MESSAGE)))
        .addMethod(
          getSendMulticastMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.SendMulticastRequest,
              rs.raf.pds.v4.z5.grpc.SendMulticastResponse>(
                service, METHODID_SEND_MULTICAST)))
        .addMethod(
          getRespondInviteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.RespondInviteRequest,
              rs.raf.pds.v4.z5.grpc.RespondInviteResponse>(
                service, METHODID_RESPOND_INVITE)))
        .addMethod(
          getListInvitesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.raf.pds.v4.z5.grpc.ListInvitesRequest,
              rs.raf.pds.v4.z5.grpc.ListInvitesResponse>(
                service, METHODID_LIST_INVITES)))
        .build();
  }

  private static abstract class ChatControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatControlBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return rs.raf.pds.v4.z5.grpc.ChatControlProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatControl");
    }
  }

  private static final class ChatControlFileDescriptorSupplier
      extends ChatControlBaseDescriptorSupplier {
    ChatControlFileDescriptorSupplier() {}
  }

  private static final class ChatControlMethodDescriptorSupplier
      extends ChatControlBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChatControlMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChatControlGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatControlFileDescriptorSupplier())
              .addMethod(getCreateRoomMethod())
              .addMethod(getInviteUsersMethod())
              .addMethod(getListRoomsMethod())
              .addMethod(getJoinRoomMethod())
              .addMethod(getGetMoreRoomMethod())
              .addMethod(getLeaveRoomMethod())
              .addMethod(getGetLastDMMethod())
              .addMethod(getSendRoomMessageMethod())
              .addMethod(getEditMessageMethod())
              .addMethod(getSendMulticastMethod())
              .addMethod(getRespondInviteMethod())
              .addMethod(getListInvitesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
