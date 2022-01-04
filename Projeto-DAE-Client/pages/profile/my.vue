<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow p-4 "  >
        <h1 class =" pb-4" style="text-align:center">Perfil</h1>
        <div v-if="user!=null">
          <div>
            <p>Nome:</p>
            <p>{{this.user.username}}</p>
          </div>
          <div>
            <p>Email:</p>
            <p>{{this.user.email}}</p>
          </div>
          <div>
            <p>Data de ingresso:</p>
            <p>{{this.user.data}}</p>
          </div>
        </div>

        <b-row >
          <b-col lg="6" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/biosinais"  >Alterar Perfil</nuxt-link></b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      user: null,
    }
  },
  created () {
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
      })
  },

  methods: {


  }
}
</script>

<style scoped>

</style>
