<template>
  <div>
    <b-container class="modal-content rounded-6 shadow p-4" >
      <caption class = "pb-4" style="text-align:center">Atualizar Registo: BPM</caption>

      <form  :disabled="!isFormValid">

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
      id: this.$route.params.id,

    }
  },
  created() {
  },
  computed: {

  },
  methods: {
    reset () {
      this.errorMsg = false
    },
    update() {
      this.$axios.$put(`/api/biosinais/bpm/${this.id}`, {
        descricao: this.descricao

      })
        .then(() => {
        if (this.$auth.user.groups =="UtilizadorNormal"){
                  this.$router.push('/biosinais/bpms/my')
                }
                else {
                  this.$router.push('/biosinais/bpms/all')
                }

        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    }
  }
}
</script>
