package com.zupedu.gabrielpedrico.controllers

import com.zupedu.gabrielpedrico.RegistraPixGrpcServiceGrpc
import com.zupedu.gabrielpedrico.dtos.NovaChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RegistraChavePixController(private val registraChavePixClient: RegistraPixGrpcServiceGrpc.RegistraPixGrpcServiceBlockingStub) {

    @Post("/pix")
    fun create(clienteId: UUID,@Valid @Body request: NovaChavePixRequest): HttpResponse<Any>{

        val grpcResponse = registraChavePixClient.registra(request.paraModeloGrpc(clienteId))

        return HttpResponse.created(location(clienteId,grpcResponse.pixId))

    }

    private fun location(clienteId: UUID,pixId: String) = HttpResponse
                                                            .uri("/api/v1/clientes/$clienteId/pix/${pixId}")
}