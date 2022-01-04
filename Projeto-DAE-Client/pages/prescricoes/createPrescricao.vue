<template>
  <div>
    <b-container class="modal-content rounded-6 shadow" >
      <caption style="text-align:center">Prescrições</caption>

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
        id="peso"
        description="Este valor é obrigatório"
        label="Inserir Peso medido:"
        label-for="peso"
        :invalid-feedback="invalidPesoFeedback"
        :state="isPesoValid"
      >
        <b-input id="peso" v-model.trim="peso" :state="isPesoValid" trim></b-input>
      </b-form-group>
      <b-form-select v-model="tipo" label="Inserir Peso medido:" :options="presType" >
        <b-input id="peso" v-model.trim="peso" :state="isPesoValid" trim></b-input>
      </b-form-select>


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

      data: null,
      tipo: null,
      descricao: null,
      utilizador: null,
      valores: [],
      errorMsg: false,
      user: null,
      presType: [
        { value: null, text: 'Selecionar opção' },
        { value: 'Exercicio', text: 'Exercicio' },
        { value: 'Medica', text: 'Medica' },
        { value: 'Nutricional', text: 'Nutricional' },
      ],
    }
  },
  created() {
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
      })
  },
  computed: {
    invalidDataFeedback () {
      if (!this.data) {
        return null
      }

      if (!isNaN(new Date(this.data)) ){
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
      if (! this.isDataValid) {
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
