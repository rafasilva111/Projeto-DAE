<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Administradores</h1>
        <h3 CLASS=" p-3" style="text-align:center">Lista Completa de Administradores</h3>
        <select v-model="selected" style="text-align:center">
          <option v-for="item in courses" :value="item" :key="item.id">
            {{"Admin: "+ item.username +" /  Email: "+ item.email }}
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
      <b-container class="modal-content rounded-6 shadow p-4" >
        <h4 class="p-1" style="text-align: center">Criar Admin</h4>
        <div class="btn btn-dark btn-sm" v-if="superUser">
          <nuxt-link class="nav-link" to="/auth/admin/registerAdmin" >Criar</nuxt-link>
        </div>
      </b-container>
    </div>
    <div class="p-4">
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Informações</h1>
        <b-table striped  :items="admins" :fields="fields" style="float:left;">
          <template v-slot:cell(actions)="row">
            <nuxt-link
              class="btn btn-dark btn-sm"
              :to="``">Ver Perfil</nuxt-link>
            <button
              class="btn btn-danger btn-sm" cli
              @click="apagar(row.item.id)">Apagar</button>
          </template>
        </b-table>
        <p class="text-danger" v-show="errorMsg">Utilizador é o super, não pode ser apagado</p>
      </b-container>
    </div>
  </div>

</template>

<script>

export default {
  data () {
    return {
      ready: false,
      data: [],
      fields: [ 'dataCriação','nome','email','estado','actions'],
      colestrol: [],
      user: null,
      criteria: "hey",
      courses: [],
      username: null,
      selected: null,
      errorMsg: null,
      admins:[],



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
    apagar: function (value){

      this.$axios.$delete('/api/user/admin/'+value)
        .then(() => {
          const indice = this.admins.findIndex(pesagem => pesagem.id === value)
          if (~indice)
            this.admins.splice(indice, 1)

          const indice2 = this.courses.findIndex(pesagem => pesagem.id === value)
          if (~indice2)
            this.courses.splice(indice2, 1)
        }).catch(error => {
        this.errorMsg = error.response.data
      })
    },

    find : function (value){
      //console.log(value)
      this.$axios.$get('/api/user/' + value.username + '/admin')
        .then((user) => {
          this.user = user
          // console.log(user);
          this.admins.push(user)
          console.log(this.admins)
        })
    },

    nameSearch: function(){
      console.log(this.username)
      this.$axios.$get('/api/user/' + this.username + '/admin')
        .then((user) => {
          this.user = user
          // console.log(user);
          this.admins.push(user)
          console.log(this.admins)
          console.log(this.$refs)
          this.$refs.table.$forceUpdate();
        })
    }, superUser() {
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
    }


  }
}

</script>
