<template>
  <div class="pt-4">
    <div>
    <b-container class="modal-content rounded-6 shadow" >
      <h1 style="text-align:center">BPMs</h1>
      <b-table striped  :items="bpms" :fields="fields" title="BPMs" style="float:left;">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-dark btn-sm"
            :to="`/biosinais/bpms/${row.item.id}/updateBPM`">Atualizar</nuxt-link>
          <button
            class="btn btn-danger btn-sm" cli
            @click="apagar(row.item.id)">Apagar</button>


        </template>
      </b-table>
      <b-row >
        <b-col lg="6" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/biosinais/bpms/createBPM"  >Inserir Registo</nuxt-link></b-col>

      </b-row>
    </b-container>
    </div>
    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <caption style="text-align:center">Gráfico</caption>
        <chartjs-line v-if="ready" v-bind:labels = "labels" v-bind:data="data"></chartjs-line>
      </b-container>
    </div>
    <div class="pt-4 pb-4">
      <b-container class="modal-content rounded-6 shadow" >
        <caption style="text-align:center">Estatísticas</caption>
        <table class="table">
          <thead>
          <tr>
            <th scope="col"></th>
            <th scope="col">Geral</th>
            <th scope="col">Estatisticas classificações</th>
            <th scope="col">Handle</th>

          </tr>
          </thead>
          <tbody>
          <tr>
            <th scope="col"></th>
            <th>Nº de registos:</th>
            <th></th>
            <th>@mdo</th>
          </tr>
          <tr>
            <th scope="col"></th>
            <th>Nº de Altos:</th>
            <th>Nº de Medios:</th>
            <th>Nº de baixos:</th>
          </tr>
          <tr>
            <th scope="row"></th>
            <td colspan="2">Larry the Bird</td>
            <td>@twitter</td>
          </tr>
          </tbody>
        </table>
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
      labels: [1,2,3],
      data: [4,3,2],
      fields: [
        {
          key: 'date',
          label: 'Data',
        },
        {
          key: 'value[0]',
          label: 'Numero de Batimentos por minuto',
        },
        {
          key: 'classification',
          label: 'Classificação',
        },
        {
          key: 'descricao',
          label: 'Descrição',
        },
        {
          key: 'actions',
          label: 'Actions',

        }
      ],
      bpms: [],
      user: null,


    }
  },
  created () {


    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/biosinais/bpm/'+this.user.id)
          .then((colestrol) => {
            this.bpms = colestrol
          })
        this.$axios.$get('/api/biosinais/bpm/'+this.user.id+'/graph')
          .then((graph) => {
            this.labels = graph.label
            this.data = graph.data

            this.ready = true

          })
      })
  },

  methods: {
    apagar: function (value){

      this.$axios.$delete('/api/biosinais/bpm/'+value)
        .then(() => {
          const indice = this.bpms.findIndex(pesagem => pesagem.id === value)
          if (~indice)
            this.bpms.splice(indice, 1)
        })
    },

  }
}
</script>
