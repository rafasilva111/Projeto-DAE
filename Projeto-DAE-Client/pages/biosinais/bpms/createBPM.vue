<template>
  <div>
    <b-container class="modal-content rounded-6 shadow" >
      <caption style="text-align:center">BPM</caption>

    <form  :disabled="!isFormValid">
      <b-form-group
        id="colestrol"
        description="Este valor é obrigatório"
        label="Inserir valor medido:"
        label-for="colestrol"
        :invalid-feedback="invalidColestrolFeedback"
        :state="isColestrolValid"
      >
      <b-input id="descricao" v-model.trim="colestrol" :state="isColestrolValid" trim></b-input>
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
      colestrol: null,
      descricao: null,
      valores: [],
      errorMsg: false,
      user: null
    }
  },
  created() {
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
      })
  },
  computed: {
    invalidColestrolFeedback () {
      if (!this.colestrol) {
        return null
      }

      let value = parseFloat(this.colestrol);
      if (isNaN(parseFloat(this.colestrol)) && isFinite(this.colestrol)){
        return "Inserir um valor válido"
      }
      if (!(value >=0 && value<=500)){
        return "Inserir um valor entre [0,500]"
      }
      return ''
    },
    isColestrolValid () {
      if (this.invalidColestrolFeedback === null) {
        return null
      }
      return this.invalidColestrolFeedback === ''
    },
    isFormValid () {
      if (! this.isColestrolValid) {
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


      this.$axios.$post('/api/biosinais/bpm/'+this.user.id+'/create', {
        value: [this.colestrol,0],

        descricao: this.descricao
      })
        .then(() => {

          this.$router.push('/biosinais/bpm/my')
        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    }
  }
}
</script>
