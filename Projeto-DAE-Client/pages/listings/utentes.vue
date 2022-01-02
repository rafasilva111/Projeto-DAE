<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Utentes</h1>
        <h3 CLASS=" p-3" style="text-align:center">Lista Completa de Utentes</h3>
        <b-select :options="courses" style="text-align:center" required value-field="code" text-field="username">
                <template v-slot:first>
                  <option :value="null" disabled>-- Select Utente --
                  </option>
                </template>
              </b-select>
                <b-row >
                  <b-col lg="6" style="text-align:center" class="p-4"><nuxt-link   class="btn btn-dark btn-sm" to="/prescricoes/createPrescricao"  >Mostrar Utente</nuxt-link></b-col>
                </b-row>
         <h3 CLASS=" p-3" style="text-align:center">Procurar Por Nome</h3>


    <form :disabled="!isFormValid">
      <b-form-group
        id="username"
        :invalid-feedback="invalidUsernameFeedback"
        :state="isUsernameValid"
      >
      <b-input id="username" v-model.trim="username" :state="isUsernameValid" trim></b-input>
      </b-form-group>



      <button class="btn btn-dark btn-sm" type="reset">Limpar</button>
      <button  class="btn btn-dark btn-sm" @click.prevent="find"  >Procurar</button>
    </form>

      </b-container>
    </div>
  </div>

</template>

<script>

export default {
  data () {
    return {
      graphData: [],
      ready: false,
      labels: [],
      data: [],
      fields: [ 'dataInicio','dataFim','doutorName','tipo','descricao','actions'],
      colestrol: [],
      user: null,
      criteria: "hey",
      courses: [],
      username: null,



    }
  },
  created () {
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/user/allusers/')
          .then((courses) => {
            this.courses = courses
              console.log(courses);
          })

      })

  },
  computed: {
    invalidUsernameFeedback () {
      if (!this.username) {
        return null
      }
      let usernameLen = this.username.length
      if (usernameLen < 3 || usernameLen > 15) {
        return 'The username must be between [3, 15] characters.'
      }
      return ''
    },
    isUsernameValid () {
      if (this.invalidUsernameFeedback === null) {
        return null
      }
      return this.invalidUsernameFeedback === ''
    },
    isFormValid () {
      if (! this.isUsernameValid) {
        return false
      }
      return true
    }
  },

  methods: {

    historico: function (row){

      var hoje = (row.dataFim).split("/",3)

      var date = new Date();
      if (new Date(hoje[2].split(" ")[0],hoje[1],hoje[0])<date) {
        return true;
      }

      return false;
    },

    ativas: function (row){

      var hoje = (row.dataFim).split("/",3)

      var date = new Date();
      if (new Date(hoje[2].split(" ")[0],hoje[1],hoje[0])<date) {
        return false;
      }

      return true;
    },

    find : function (){
      var username = this.username


        this.$axios.$get('/api/user/'+username+'/registers')
          .then((username) => {
          this.username = username
          this.$axios.$get('/api/user/' + username + '/')
            .then((user) => {
              this.user = user
              console.log(user);
            })

        })
      }
  }
}

</script>
