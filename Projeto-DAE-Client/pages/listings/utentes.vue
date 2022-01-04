<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Utentes</h1>
        <h3 CLASS=" p-3" style="text-align:center">Lista Completa de Utentes</h3>

        <select v-model="selected" style="text-align:center"  @change="filterItem(selected)">
          <option v-for="item in courses" :value="item" :key="item.id">
            {{"Utente: "+ item.username +" /  Email: "+ item.email }}
          </option>
        </select>
        <div class="pb-4"></div>
        <button
          class="btn btn-dark btn-sm" cli
          @click="find(selected)">Procurar</button>

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
        <button
          class="btn btn-dark btn-sm" cli
          @click="nameSearch()">Procurar</button>
      </b-container>
    </div>
    <div class="pt-4">
      <b-container  class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Informações</h1>
        <b-table  ref="table" striped over :items="utentes" :fields="fields2" style="float:left;">
          <template v-slot:cell(actions)="row">
            <nuxt-link
              class="btn btn-dark btn-sm"
              :to="``">Ver Perfil</nuxt-link>
            <button
              class="btn btn-danger btn-sm" cli
              @click="apagar(row.item.id)">Apagar</button>
          </template>
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
      fields: [ 'nome','email','regCategoria1','regCategoria2','regCategoria3','nomeMedicoAssociado','totalDePrescrições'],
      colestrol: [],
      user: null,
      criteria: "hey",
      courses: [],
      utentes:[],
      username: null,
      selected:null,
      errorMsg:null,
      fields2: [
        {
          key: 'data',
          label: 'Data Registo',
        },
        {
          key: 'username',
          label: 'Nome',
        },
        {
          key: 'email',
          label: 'Email',
        },
        {
          key: 'doutorUsername',
          label: 'Medico',
        },
        {
          key: 'actions',
          label: 'Actions',

        }
      ]



    }
  },
  created () {
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/user/allusers/')
          .then((courses) => {
            this.courses = courses
              //console.log(courses);
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
    apagar: function (value){

      this.$axios.$delete('/api/user/user/'+value)
        .then(() => {
          const indice = this.utentes.findIndex(pesagem => pesagem.id === value)
          if (~indice)
            this.utentes.splice(indice, 1)
          const indice2 = this.courses.findIndex(pesagem => pesagem.id === value)
          if (~indice2)
            this.courses.splice(indice2, 1)
        })
    },
    find : function (value){
          //console.log(value)
          this.$axios.$get('/api/user/' + value.username + '/utente')
            .then((user) => {
              this.user = user
             // console.log(user);
              this.utentes.push(user)
              console.log(this.utentes)
            })
      },

    filterItem: function(value) {
      if (value != null) {
        //console.log("Selected no filter")
       // console.log(this.utentes)
      }else{
        //console.log("Selected "+this.selected.measurement+" / "+this.selected.unit)
        console.log("Dead")
      }
    },


    nameSearch: function(){
      console.log(this.username)
      this.$axios.$get('/api/user/' + this.username + '/utente')
        .then((user) => {
          this.user = user
          // console.log(user);
          this.utentes.push(user)
          console.log(this.utentes)
          console.log(this.$refs)
          this.$refs.table.$forceUpdate();
        })
    }

  }
}

</script>
