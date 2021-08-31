package com.zupedu.gabrielpedrico.configs

import com.zupedu.gabrielpedrico.RegistraPixGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class KeyManagerGrpcFactory (@GrpcChannel("key-manager-grpc") val channel:ManagedChannel) {

    @Singleton
    fun registraChave() = RegistraPixGrpcServiceGrpc.newBlockingStub(channel)
}