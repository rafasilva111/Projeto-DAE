<template>
  <div>
    <b-container class="modal-content rounded-6 shadow" >
      <caption style="text-align:center">Pesagem</caption>

    <form  :disabled="!isFormValid">
      <b-form-group
        id="colestrol"
        description="Este valor é obrigatório"
        label="Inserir valor medido:"
        label-for="colestrol"
        :invalid-feedback="invalidAlturaFeedback"
        :state="isAlturaValid"
      >
        <b-input id="altura" v-model.trim="altura" :state="isAlturaValid" trim></b-input>
      </b-form-group>
      <b-form-group
        id="peso"
        description="Este valor é obrigatório"
        label="Inserir Peso medido:"
        label-for="peso"
        :invalid-feedback="invalidPesoFeedback"
        :state="isPesoValid"
      >
        <b-input id="peso" v-model.trim="peso" :state="isPesoValid" trim></b-input>
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
      altura: null,
      peso: null,
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
    invalidAlturaFeedback () {
      if (!this.altura) {
        return null
      }

      let value = parseFloat(this.altura);
      if (isNaN(parseFloat(this.altura)) && isFinite(this.altura)){
        return "Inserir um valor válido"
      }
      if (!(value >=0 && value<3)){
        return "Inserir um valor entre [0,3] (m)"
      }
      return ''
    },
    isAlturaValid () {
      if (this.invalidAlturaFeedback === null) {
        return null
      }
      return this.invalidAlturaFeedback === ''
    },

    invalidPesoFeedback () {
      if (!this.peso) {
        return null
      }

      let value = parseFloat(this.peso);
      if (isNaN(parseFloat(this.peso)) && isFinite(this.peso)){
        return "Inserir um valor válido"
      }
      if (value>=50000){
        return "É texugo!!!!"
      }
      if (!(value >0 && value<=300)){
        return "Inserir um valor entre [1,300]"
      }

      return ''
    },
    isPesoValid () {
      if (this.invalidPesoFeedback === null) {
        return null
      }
      return this.invalidPesoFeedback === ''
    },
    isFormValid () {
      if (! this.isPesoValid) {
        return false
      }if (! this.isAlturaValid) {
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

      this.$axios.$post('/api/biosinais/pesagem/'+this.user.id+'/create', {
        value: [this.altura,this.peso],

        descricao: this.descricao
      })
        .then(() => {

          this.$router.push('/biosinais/pesagem/my')
        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    }
  }
}
</script>
