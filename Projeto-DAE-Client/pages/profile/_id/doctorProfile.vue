<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow p-4 "  >
        <h1 class =" pb-4" style="text-align:center">Perfil</h1>
        <b-container class="modal-content rounded-6 shadow p-4 pb-4" size="200px" >

          <div v-if="user!=null">
            <h3 style="text-align: center">Info</h3>
            <div>
              <p style="text-align: center">Nome: {{this.user.username}}</p>
            </div>
            <div>
              <p style="text-align: center">Email: {{this.user.email}}</p>
            </div>
            <div>
              <p style="text-align: center">Data de ingresso: {{this.user.data}}</p>
            </div>
          </div>

          <nuxt-link  style="text-align: center" class="btn btn-dark btn-sm" to="/biosinais"  >Alterar Perfil</nuxt-link>
          </b-container>

      </b-container>
    </div>
    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Utentes Associados</h1>
        <b-table striped  :items="utente" :fields="fields" style="float:left;">
        </b-table>

      </b-container>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      user: null,
      id: this.$route.params.id,
      colestrol:[],
      utente:[],
      fields:[
        {
          key: 'username',
          label: 'Nome',
        },
        {
          key: 'email',
          label: 'Email',
        },
      ]
    }
  },
  created () {
    this.$axios.$get('/api/user/doc/'+this.id)
      .then((user) => {
        this.user = user
        console.log(user)
        this.$axios.$get('/api/user/utentesDoc/'+this.user.username)
          .then((colestrol) => {

            this.colestrol = colestrol
            this.utente = this.user.utilizadorDTOList
            console.log(colestrol)
          })


      })

  },
computed:{
  superUser() {
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
},
  methods: {


  }
}
</script>

<style scoped>

</style>
