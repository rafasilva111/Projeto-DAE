<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow" >
        <h1 CLASS=" p-3" style="text-align:center">Prescrições</h1>
        <b-table striped  :items="colestrol" :fields="fields" style="float:left;">
          <template v-slot:cell(actions)="row">
            <nuxt-link
              class="btn btn-dark btn-sm"
              :to="`/biosinais/colestrol/${row.item.id}/updateColestrol`">Atualizar</nuxt-link>
            <button
              class="btn btn-danger btn-sm" cli
              @click="apagar(row.item.id)">Apagar</button>
          </template>
        </b-table>
        <b-row >
          <b-col lg="6" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/biosinais/colestrol/createColestrol"  >Inserir Registo</nuxt-link></b-col>
        </b-row>
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
      fields: [ 'date','value','classification','descricao','actions'],
      colestrol: [],
      user: null,


    }
  },
  created () {
    console.log(this.$auth.user.groups=='UtilizadorNormal')
    console.log("here"+this.$auth.user.groups)
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/prescricoes/'+this.user.id)
          .then((colestrol) => {
            this.colestrol =
              colestrol
          })

      })
  },

  methods: {
    apagar: function (value){

      this.$axios.$delete('/api/biosinais/colestrol/'+value)
        .then(() => {
          this.$router.push('/biosinais/colestrol/my')
        })
    },

  }
}
</script>
