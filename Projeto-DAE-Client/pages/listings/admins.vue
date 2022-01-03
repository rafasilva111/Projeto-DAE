<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Administradores</h1>
        <h3 CLASS=" p-3" style="text-align:center">Lista Completa de Administradores</h3>
        <b-select :options="courses" v-model = "selected" style="text-align:center" required value-field="code" text-field="username">
                <template v-slot:first>
                  <option :value="null" disabled>-- Select Administrador --
                  </option>
                </template>
              </b-select>
                <b-row >
                  <b-col lg="6" style="text-align:center" class="p-4"><nuxt-link   class="btn btn-dark btn-sm" to="/prescricoes/createPrescricao"  >Mostrar Administrador</nuxt-link></b-col>
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

    </form>
        <nuxt-link  style="text-align:center" class="btn btn-dark btn-sm" to=""  >Procurar</nuxt-link>

      </b-container>
    </div>
    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Informações</h1>
        <b-table striped  :items="colestrol" :filter="criteria" :filter-function="historico" :fields="fields" style="float:left;">
        </b-table>

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
      fields: [ 'dataCriação','nome','email','estado'],
      colestrol: [],
      user: null,
      criteria: "hey",
      courses: [],
      username: null,
      selected: null,



    }
  },
  created () {




    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/user/alladmins/')
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


  }
}

</script>
