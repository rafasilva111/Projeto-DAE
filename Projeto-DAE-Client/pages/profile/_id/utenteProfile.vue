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

      <b-container  v-if="superUser || doctor " class="modal-content rounded-6 shadow p-4" >
        <h4 class="p-1" style="text-align: center">Utentes Associados</h4>
        <div class="btn btn-dark btn-sm">
          <nuxt-link class="nav-link" to="/auth/admin/registerDoctor" >Criar</nuxt-link>
        </div>
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
    }
  },
  created () {
    this.$axios.$get('/api/user/'+this.id+'/registers')
      .then((user) => {
        this.user = user
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
