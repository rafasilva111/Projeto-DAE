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

      <div class="pt-1 pb-1">
        <p>Escolher tipo de prescricao</p>
        <b-form-select v-model="tipo" label="Inserir Peso medido:" :options="presType" >
          <b-input id="peso" v-model.trim="peso" :state="isPesoValid" trim></b-input>
        </b-form-select>
      </div>
      <div class="pt-1 pb-3">
        <p>Escolher utilizador:</p>
        <b-form-select v-model="utilizador"  label="Inserir Peso medido:" :options="userList" >
          <b-input id="peso" v-model.trim="peso" :state="isPesoValid" trim></b-input>
        </b-form-select>
      </div>

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
      loaded: false,
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
        this.loaded = true
      })
  },
  computed: {
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
      }if ( this.utilizador == null) {
        return false
      }
      return true
    },
    userList(){

      if (this.user!= null && this.user.utilizadorDTOList!=null){


          let reformattedArray = this.user.utilizadorDTOList.map(obj => {
          let rObj = {"value":obj.id,"text":obj.username}

          return rObj
        })

        reformattedArray.unshift({ value: null, text: 'Selecionar opção' })

        return reformattedArray;

      }

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
      console.log(this.utilizador.value + " here " +this.utilizador)

      this.$axios.$post('/api/prescricoes/'+this.user.id+'/create', {
        dataFim: this.data+"",
        descricao: this.descricao,
        utilizadorNormalId: this.utilizador,
        doutorId: this.user.id
      })
        .then(() => {

          this.$router.push('/prescricoes/all' +
            '')
        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    }
  }
}
</script>
