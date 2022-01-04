<template>
  <div>
    <b-container class="modal-content rounded-6 shadow p-4" >
      <caption class = "pb-4" style="text-align:center">Atualizar Registo: Prescricao</caption>

      <form  :disabled="!isFormValid">
        <b-form-group
          id="Data"
          description="Este valor é obrigatório, utilizar formato dd/mm/yyyy"
          label="Data de fim da prescrição:"
          label-for="Data"
          :invalid-feedback="invalidDataFeedback"
          :state="isDataValid"
        >
          <b-input id="altura" v-model.trim="data" :state="isDataValid" trim></b-input>
        </b-form-group>

        <b-form-group
          id="descricao"
          label="Inserir anotações:"
          description="Este campo serve para comunicar alguma informação que seja pertinente para o médico"
          label-for="descricao"


        >
          <b-input id="descricao" v-model.trim="descricao"  trim />
        </b-form-group>


        <p class="text-danger" v-show="errorMsg">{{ errorMsg }}</p>
        <div class="pb-4">


          <button class="btn btn-dark btn-sm" type="reset" @click="reset">Reset</button>
          <button class="btn btn-success btn-sm"     @click.prevent="update" >Atualizar</button>

          <button class="btn btn-dark btn-sm float-right" right  @click.prevent="voltar" >Voltar</button>
        </div>
      </form>
    </b-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      descricao: null,
      errorMsg: false,
      data: null,
      id: this.$route.params.id,

    }
  },
  created() {
  },
  methods: {
    reset () {
      this.errorMsg = false
    },
    update() {
      this.$axios.$put(`/api/prescricoes/${this.id}`, {
        descricao: this.descricao,
        dataFim: this.data

      })
        .then(() => {
          if (this.$auth.user.groups =="UtilizadorNormal"){
            this.$router.push('/prescricoes/my')
          }
          else {
            this.$router.push('/prescricoes/all')
          }

        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    }
  },
  computed:{
    invalidDataFeedback () {
      if (!this.data) {
        return null
      }

      if (isNaN(new Date(this.data)) ){
        return "Inserir um valor válido"
      }

      return ''
    },
    isDataValid () {
      if (this.invalidDataFeedback === null) {
        return null
      }
      return this.invalidDataFeedback === ''
    },

    isFormValid () {
      if (! this.isDataValid) {
        return false
      }if ( this.tipo== null) {
        return false
      }
      return true
    },
  }
}
</script>
