<template>
  <div>
    <b-container class="modal-content rounded-6 shadow" >
      <h1 class="p-4" style="text-align:center">{{this.outrosCat.name}}</h1>

    <form  :disabled="!isFormValid">
      <b-form-group
        id="valor"
        description="Este valor é obrigatório"
        label="Inserir valor medido:"
        label-for="valor"
        :invalid-feedback="invalidValorFeedback"
        :state="isValorValid"
      >
        <b-input id="valor" v-model.trim="valor" :state="isValorValid" trim></b-input>
      </b-form-group>
      <b-form-group
        id="descricao"
        label="Inserir anotações:"
        description="Este campo serve para comunicar alguma informação que seja pertinente para o médico"
        label-for="descricao"

      >
        <b-input id="password" v-model.trim="descricao"  trim />
      </b-form-group>

      <p class="text-danger" v-show="errorMsg">{{ errorMsg }}</p>
      <div class="pb-4">


        <button class="btn btn-dark btn-sm" type="reset" @click="reset">Reset</button>
        <button class="btn btn-success btn-sm"     @click.prevent="create" :disabled="!isFormValid">Inserir</button>

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
      valor: null,
      descricao: null,
      errorMsg: false,
      user: null,
      id: this.$route.params.id,
      outrosCat: [],
    }
  },
  created() {
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
      })
    this.$axios.$get('/api/admin/outro/'+this.id)
      .then((outroCat) => {
        this.outrosCat = outroCat
      })

  },
  computed: {

    invalidValorFeedback () {
      if (!this.valor) {
        return null
      }


      if (isNaN(parseFloat(this.valor)) && isFinite(this.valor)){
        return "Inserir um valor válido"
      }

      if (!( this.valor>=this.outrosCat.minValues && this.valor<=this.outrosCat.maxValues)){
        return "Inserir um valor entre ["+this.outrosCat.minValues+","+this.outrosCat.maxValues+"]"
      }

      return ''
    },
    isValorValid () {
      if (this.invalidValorFeedback === null) {
        return null
      }
      return this.invalidValorFeedback === ''
    },
    isFormValid () {
      if (! this.isValorValid) {
        return false
      }
      return true
    }
  },
  methods: {
    reset () {
      this.errorMsg = false
    },
    voltar() {
      this.$router.push('/biosinais/colestrol/my')
    },
    create() {

      this.$axios.$post('/api/biosinais/outro/'+this.user.id+'/create', {
        value: this.valor,
        outroCategoriesID: this.id,
        descricao: this.descricao
      })
        .then(() => {
          if (this.$auth.user.groups =="UtilizadorNormal"){
            this.$router.push('/biosinais/outros/my')
          }
          else {
            this.$router.push('/biosinais/outros/all')
          }
        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    }
  }
}
</script>
