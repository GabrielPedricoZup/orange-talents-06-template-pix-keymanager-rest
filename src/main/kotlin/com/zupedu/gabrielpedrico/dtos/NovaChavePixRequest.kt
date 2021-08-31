package com.zupedu.gabrielpedrico.dtos

import com.zupedu.gabrielpedrico.RegistraChavePixRequest
import com.zupedu.gabrielpedrico.TipoDeChave
import com.zupedu.gabrielpedrico.TipoDeConta
import com.zupedu.gabrielpedrico.validations.ValidPixKey
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
class NovaChavePixRequest(@field:NotNull val tipoDeConta: TipoDeContaRequest?,
                          @field:Size(max = 77) val chave: String?,
                          @field:NotNull val tipoDeChave: TipoDeChaveRequest?){

    fun paraModeloGrpc(clienteId: UUID): RegistraChavePixRequest{
        return RegistraChavePixRequest.newBuilder()
            .setClientId(clienteId.toString())
            .setTipoDeConta(tipoDeConta?.atributoGrpc ?: TipoDeConta.UNKNOWN_TIPO_CONTA)
            .setTipoDeChave(tipoDeChave?.atributoGrpc ?: TipoDeChave.UNKNOWN_CHAVE)
            .setChave(chave ?: "")
            .build()
    }

}

enum class TipoDeContaRequest(val atributoGrpc: TipoDeConta){

    CONTA_CORRENTE(TipoDeConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoDeConta.CONTA_POUPANCA)
}

enum class TipoDeChaveRequest(val atributoGrpc: TipoDeChave){

    CPF(TipoDeChave.CPF){
        override fun valida(chave: String?): Boolean{
            if(chave.isNullOrBlank()){
                return false
            }

            return CPFValidator().run {
                initialize(null)
                isValid(chave,null)
            }
        }
    },

    CELULAR(TipoDeChave.CELULAR){
        override fun valida(chave: String?): Boolean{
            if(chave.isNullOrBlank()){
                return false
            }

            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }

    },

    EMAIL(TipoDeChave.EMAIL){
        override fun valida(chave: String?): Boolean{
            if(chave.isNullOrBlank()){
                return false
            }

            return EmailValidator().run {
                initialize(null)
                isValid(chave,null)
            }
        }

    },



    ALEATORIA(TipoDeChave.ALEATORIA){
        override fun valida(chave: String?) = chave.isNullOrBlank()

        };

    abstract fun valida(chave: String?):Boolean

}