<template>
  <div>
    <b-container class="modal-content rounded-6 shadow" >
      <caption style="text-align:center">Pesagem</caption>

    <form  :disabled="!isFormValid">
      <b-form-group
        id="name"
        description="Este valor é obrigatório"
        label="Inserir Nome:"
        label-for="name"
        :invalid-feedback="invalidAlturaFeedback"
        :state="isAlturaValid"
      >
        <b-input id="name" v-model.trim="name" :state="isAlturaValid" trim></b-input>
      </b-form-group>
      <b-form-group
        id="maxValue"
        description="Este valor é obrigatório"
        label="Inserir o Valor Maximo:"
        label-for="maxValue"
        :invalid-feedback="invalidMaxFeedback"
        :state="isMaxValid"
      >
        <b-input id="maxValue" v-model.trim="maxValue" :state="isMaxValid" trim></b-input>
      </b-form-group>
      <b-form-group
        id="minValue"
        description="Este valor é obrigatório"
        label="Inserir o Valor Minimo:"
        label-for="minValue"
        :invalid-feedback="invalidMinFeedback"
        :state="isMinValid"
      >
        <b-input id="minValue" v-model.trim="minValue" :state="isMinValid" trim></b-input>
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
      name: null,
      maxValue: null,
      minValue: null,
      errorMsg: false,
    }
  },
  created() {

  },
  computed: {
    invalidAlturaFeedback () {
      if (!this.name) {
        return null
      }
      //verifica se string
      if (typeof this.name != "string"){
        return 'Inserir apenas letras'
      }

      if (!(this.name.length >=0 && this.name.length<50)){
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

    invalidMaxFeedback () {
      if (!this.maxValue) {
        return null
      }

      if (isNaN(this.maxValue)){
        return "Inserir um numero."
      }

      if (!(this.maxValue >0 && this.maxValue<=5000)){
        return "Inserir um valor entre [1,5000]"
      }

      return ''
    },
    isMaxValid () {
      if (this.invalidMaxFeedback === null) {
        return null
      }
      return this.invalidMaxFeedback === ''
    },

    invalidMinFeedback () {
      if (!this.minValue) {
        return null
      }
      if (this.minValue>this.maxValue){
        return "Valor minimo tem que ser maior que valor maximo"
      }
      if (isNaN(this.minValue)){
        return "Inserir um numero."
      }

      if (!(this.minValue >0 && this.minValue<=5000)){
        return "Inserir um valor entre [1,5000]"
      }

      return ''
    },
    isMinValid () {
      if (this.invalidMinFeedback === null) {
        return null
      }
      return this.invalidMinFeedback === ''
    },
    isFormValid () {
      if (! this.isMaxValid) {
        return false
      }if (! this.isAlturaValid) {
        return false
      }if (! this.isMinValid) {
        return false
      }
      if (this.maxValue<this.minValue){
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
      this.$router.push('/admin/biosinais/all')
    },
    create() {
      this.$axios.$post('/api/admin/outro/create', {
        name: this.name,
        maxValues: this.maxValue,
        minValues: this.minValue,
      })
        .then(() => {

          this.$router.push('/admin/biosinais/all')
        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    }
  }
}
</script>
