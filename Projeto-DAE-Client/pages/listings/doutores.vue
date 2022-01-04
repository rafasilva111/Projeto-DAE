<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Doutores</h1>
        <h3 CLASS=" p-3" style="text-align:center">Lista Completa de Doutores</h3>

        <select v-model="selected" style="text-align:center">
          <option v-for="item in courses" :value="item" :key="item.id">
            {{"Doutor: "+ item.username +" /  Email: "+ item.email }}
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
      <b-container  v-if="superUser" class="modal-content rounded-6 shadow p-4" >
        <h4 class="p-1" style="text-align: center">Criar Doutor</h4>
        <div class="btn btn-dark btn-sm">
          <nuxt-link class="nav-link" to="/auth/admin/registerDoctor" >Criar</nuxt-link>
        </div>
        </b-container>
    </div>



    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Informações</h1>
        <b-table striped  :items="doutores"  :fields="fields" style="float:left;">
        </b-table>

      </b-container>
    </div>
    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Utentes Associados</h1>
        <b-table striped  :items="colestrol" :fields="fields" style="float:left;">
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
      fields: [ 'nome','email','regCategoria1','regCategoria2','regCategoria3','totalDePrescrições'],
      colestrol: [],
      user: null,
      criteria: "hey",
      courses: [],
      username: null,
      doutores:[],
      selected:null,



    }
  },
  created () {




    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/user/alldocs/')
          .then((courses) => {
            this.courses = courses
              console.log(courses);
          })

      })

  },
  computed:
    {superUser() {
      if (this.$auth.user.groups =="Administrador"){
        return true;
      }
      else {
        return false;
      }
    },
    doctor() {
      if (this.$auth.user.groups =="Doutor"){
        return true;
      }
      else {
        return false;
      }
    },
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
    find : function (value){
      //console.log(value)
      this.$axios.$get('/api/user/' + value.username + '/doutor')
        .then((user) => {
          this.user = user
          // console.log(user);
          this.doutores.push(user)
          console.log(this.doutores)
        })
    },

    nameSearch: function(){
      console.log(this.username)
      this.$axios.$get('/api/user/' + this.username + '/doutor')
        .then((user) => {
          this.user = user
          // console.log(user);
          this.doutores.push(user)
          console.log(this.doutores)
          console.log(this.$refs)
          this.$refs.table.$forceUpdate();
        })
    }


  }
}

</script>
